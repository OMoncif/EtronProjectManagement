package com.example.demo.services;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.example.demo.constants.EtronPrjConstants;
import com.example.demo.models.AbonnementPlan;
import com.example.demo.models.BorneRecharge;
import com.example.demo.models.Contrat;
import com.example.demo.models.Facture;
import com.example.demo.models.Paiement;
import com.example.demo.models.Recharge;
import com.example.demo.models.User;
import com.example.demo.repositories.AbonnementPlanRepository;
import com.example.demo.repositories.BorneRechargeRepository;
import com.example.demo.repositories.ContratRepository;
import com.example.demo.repositories.FactureRepository;
import com.example.demo.repositories.PaiementRepository;
import com.example.demo.repositories.RechargeRepository;
import com.example.demo.repositories.UserRepository;
import com.example.demo.repositories.VoitureRepository;
import com.example.demo.utils.EmailUtils;
import com.example.demo.utils.EtronPrjUtils;
import com.google.common.base.Strings;
import com.example.demo.JWT.CustomerUserDetailsService;
import com.example.demo.JWT.JwtFilter;
import com.example.demo.JWT.JwtUtil;


@Service
public class EtronAbonnementService {
	@Autowired
	AuthenticationManager authenticationManager;
	
	@Autowired
	CustomerUserDetailsService customerUserDetailsService;
	
	@Autowired
	JwtUtil jwtUtil;
	
	@Autowired
	JwtFilter jwtFilter;
	
	@Autowired
	EmailUtils emailUtils;
	
	@Autowired
	private UserRepository userrepos;
	
	@Autowired
	private ContratRepository contratrepos;
	
	@Autowired
	private AbonnementPlanRepository planrepos;
	
	@Autowired
	private PaiementRepository paiementrepos;
	
	@Autowired
	private VoitureRepository voiturerepos;
	
	@Autowired
	private BorneRechargeRepository bornerepos;
	
	@Autowired
	private RechargeRepository rechargerepos;
	
	@Autowired
	private FactureRepository facturerepos;
	
	
	public ResponseEntity<String> inscrireUtilisateur(Map<String, String> requestMap) {
		System.out.println("Request Map: " + requestMap);
		System.out.println("Validation Result: " + validateSignUpMap(requestMap));
		try {
            if (validateSignUpMap(requestMap)) {
            	
                User user = userrepos.findByEmail(requestMap.get("email"));
                if (Objects.isNull(user)) {
                	userrepos.save(getUserFromMap(requestMap));
                    return EtronPrjUtils.getResponseEntity("Successfully Registered", HttpStatus.OK);
                } else {
                    return EtronPrjUtils.getResponseEntity("Email already exists", HttpStatus.BAD_REQUEST);
                }
            } else {
                return EtronPrjUtils.getResponseEntity(EtronPrjConstants.INVALID_DATA, HttpStatus.BAD_REQUEST);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return EtronPrjUtils.getResponseEntity(EtronPrjConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }


    private boolean validateSignUpMap(Map<String, String> requestMap) {
        if(requestMap.containsKey("name") && requestMap.containsKey("contactnumber")
                && requestMap.containsKey("email") && requestMap.containsKey("password") && requestMap.containsKey("adresse") && requestMap.containsKey("prenom") && requestMap.containsKey("role") && requestMap.containsKey("prenom"))
        {
        	if(requestMap.get("name") != "" && !TestService.isInteger(requestMap.get("name")) && !TestService.isStartWithNumber(requestMap.get("name")) && requestMap.get("email") != "" && !TestService.isInteger(requestMap.get("email")) && requestMap.get("adresse") != "" && !TestService.isInteger(requestMap.get("adresse")) && requestMap.get("prenom") != "" && !TestService.isInteger(requestMap.get("prenom")) && !TestService.isStartWithNumber(requestMap.get("prenom")) && requestMap.get("role") != "" && !TestService.isInteger(requestMap.get("role")) && !TestService.isStartWithNumber(requestMap.get("role"))) {
        		return true;
        	}
        	else {
        		return false;
        	}
            
        }
        return false;
    }

    private User getUserFromMap(Map<String, String> requestMap) {
        User user = new User();
        user.setName(requestMap.get("name"));
        user.setPrenom(requestMap.get("prenom"));
        user.setContactnumber(requestMap.get("contactnumber"));
        user.setEmail(requestMap.get("email"));
        user.setPassword(requestMap.get("password"));
        user.setAdresse(requestMap.get("adresse"));
        user.setDateInscription(LocalDate.now());
        user.setModeleVoiture(requestMap.get("modeleVoiture"));
        user.setRole(requestMap.get("role"));
        return user;
    }
    
    public ResponseEntity<String> login(Map<String, String> requestMap) {
		System.out.println("Inside login");
		try {
			if(!requestMap.containsKey("email") || !requestMap.containsKey("password")) {
				return new ResponseEntity<String>("{\"message\":\""+"Bad Credentials."+"\"}", HttpStatus.BAD_REQUEST);
			} else {
				Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(requestMap.get("email"), requestMap.get("password")));
				
				if(auth.isAuthenticated()) {
					return new ResponseEntity<String>("{\"token\":\"" + jwtUtil.generateToken(customerUserDetailsService.getUserDetails().getEmail(), customerUserDetailsService.getUserDetails().getRole()) + "\"}", HttpStatus.OK);
				}
			}
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<String>("{\"message\":\""+"Bad Credentials."+"\"}", HttpStatus.BAD_REQUEST);
	}
    
    public ResponseEntity<String> checkToken() {
		return EtronPrjUtils.getResponseEntity("true", HttpStatus.OK);
	}

	public ResponseEntity<String> changePassword(Map<String, String> requestMap) {
		try {
			User userObj = userrepos.findByEmail(jwtFilter.getCurrentuser());
			if(!userObj.equals(null)) {
				if(userObj.getPassword().equals(requestMap.get("oldPassword"))) {
					userObj.setPassword(requestMap.get("newPassword"));
					userrepos.save(userObj);
					emailUtils.sendSimpleMessage(jwtFilter.getCurrentuser(), "Password changed", "Account password is changed.", null);
					return EtronPrjUtils.getResponseEntity("Password Updated Successfully", HttpStatus.OK);
				}
				return EtronPrjUtils.getResponseEntity("Incoreect Old Password", HttpStatus.BAD_REQUEST);
			}
			return EtronPrjUtils.getResponseEntity(EtronPrjConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return EtronPrjUtils.getResponseEntity(EtronPrjConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	public ResponseEntity<String> forgotPassword(Map<String, String> requestMap) {
		try {
			User userObj = userrepos.findByEmail(requestMap.get("email"));
			if(!Objects.isNull(userObj) && !Strings.isNullOrEmpty(userObj.getEmail())) {
				emailUtils.forgotPasswordMail(userObj.getEmail(), "Your Login Credentials for Etron Management System", userObj.getPassword());
			}
			return EtronPrjUtils.getResponseEntity("Check your mail box...", HttpStatus.OK);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return EtronPrjUtils.getResponseEntity(EtronPrjConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
	}
    
	
    
	//Map<String, String> requestMap
    
	public ResponseEntity<String> choisirFormuleAbonnement(Map<String,String> requestMap) {
		
		try {
			if(requestMap.get("typeAbonnement") != "" ) {
				if(requestMap.get("typeAbonnement").equals("plus") || requestMap.get("typeAbonnement").equals("pro") || requestMap.get("typeAbonnement").equals("basic")) {
					User user = userrepos.findByEmail(jwtFilter.getCurrentuser());
					boolean eligibleAbonnementGratuit = estEligibleAbonnementGratuit(user);
					System.out.println(eligibleAbonnementGratuit);
			
			        AbonnementPlan abonnement = planrepos.findByType(requestMap.get("typeAbonnement"));
			
			        double fraisMensuels = abonnement.getFraismois(); 
			
			        if (eligibleAbonnementGratuit && requestMap.get("typeAbonnement").equals("pro") ) {
			            fraisMensuels = 0.0; // Abonnement gratuit la première année pour les propriétaires de l'e-tron.
			        }
			        if( user.getContrat() == null || user.getContrat().getDateFin().isBefore(LocalDate.now()) ) {
			        	Contrat contrat = new Contrat();
			            contrat.setUser(user);
			            contrat.setDateDebut(LocalDate.now());
			            contrat.setDateFin(LocalDate.now().plus(1, ChronoUnit.MONTHS));
			            contrat.setFraisMois(fraisMensuels);
			            contrat.setPlan(abonnement);
			            
			            contratrepos.save(contrat);
			            
			            return EtronPrjUtils.getResponseEntity("Abonnement Successfully Chosed & Contrat Created & Waiting For Your Paiement ", HttpStatus.OK);
			        }else {
			        	return EtronPrjUtils.getResponseEntity("Old Contract Not Ended Yet", HttpStatus.INTERNAL_SERVER_ERROR);
			        }
				
		        }else {
		        	return EtronPrjUtils.getResponseEntity("Please enter the right TypeAbonnement (plus,pro,basic)", HttpStatus.BAD_REQUEST);
				
		        }
			
	        }else {
	        	return EtronPrjUtils.getResponseEntity("Missing Argument's Type Value", HttpStatus.BAD_REQUEST);
	        }
	        
        
		} catch(Exception e) {
			e.printStackTrace();
		}
		return EtronPrjUtils.getResponseEntity(EtronPrjConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
        		
    }
	
	// Méthode pour effectuer un paiement mensuel
	public ResponseEntity<String> effectuerPaiementAvecCode(Map<String,String> requestMap) {
		try {
			if(requestMap.get("typeAbonnement") != "" && !TestService.isInteger(requestMap.get("typeAbonnement"))) {
				User user = userrepos.findByEmail(jwtFilter.getCurrentuser());
				AbonnementPlan abonnement = user.getContrat().getPlan();
				Contrat contrat = user.getContrat();
				
				Month currentMonth = LocalDate.now().getMonth();
				int mois = currentMonth.getValue(); // Get the month value (1 for January, 2 for February, etc.)

				Paiement paiement1 = paiementrepos.getPaiementByUserAndMonth(user.getId(), mois);
				if(abonnement != null && abonnement.getType().equals(requestMap.get("typeAbonnement"))) {
					if (paiement1 == null) {
			            Paiement paiement = new Paiement();
			            paiement.setUser(user);
			            paiement.setDatePaiement(LocalDate.now());
			            paiement.setMontant(contrat.getFraisMois());

			            paiementrepos.save(paiement);


			            return EtronPrjUtils.getResponseEntity(" Paiement Successfully Registered ", HttpStatus.OK); // Paiement réussi
			        }else {
			        	return EtronPrjUtils.getResponseEntity(" You paid already for this month , you don't need to do it again :) ", HttpStatus.BAD_REQUEST);
			        }
				}else {
					return EtronPrjUtils.getResponseEntity(" the Code you've entered is not true , please try again ", HttpStatus.BAD_REQUEST);
				}
				
			}else {
				return EtronPrjUtils.getResponseEntity(" Missing Argument's Type Value ", HttpStatus.BAD_REQUEST);
			}
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		

        return EtronPrjUtils.getResponseEntity(EtronPrjConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR); // Code invalide

	        
	}
	
	// Paiement facture impayée
	public ResponseEntity<String> payerFacture() {
		try {
			User user = userrepos.findByEmail(jwtFilter.getCurrentuser());
			Facture facture = user.getContrat().getFacture();
			
			facture.setStatus("payée");
			facturerepos.save(facture);

	        return EtronPrjUtils.getResponseEntity("Paiement Successfully Registered", HttpStatus.OK); // Paiement réussi
	        
		} catch(Exception e) {
			e.printStackTrace();
		}
        return EtronPrjUtils.getResponseEntity(EtronPrjConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR); // Code invalide

	        
	}
	 
	 // Connaitre tous les bornes qui se trouvent autour d'un utilisateur d'un rayon max de 5 KM
	public ResponseEntity<List<BorneRecharge>> ListerBornesRechargeUser(Map<String,String >requestMap){
		System.out.println(requestMap);
		try {
			List<BorneRecharge> bornes = bornerepos.findAll();
			List<BorneRecharge> bornesProches = new ArrayList<BorneRecharge>();
			
			for (BorneRecharge borne : bornes) {
			    double distance = calculerDistance(Double.parseDouble(requestMap.get("userLatitude")), Double.parseDouble(requestMap.get("userLongitude")), borne.getLatitude(), borne.getLongitude());
			    
			    if (distance <= 5) {
			        bornesProches.add(borne);
			    }
			}
			return new ResponseEntity<>(bornesProches, HttpStatus.OK);
		} catch(Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
		
		
	}
	
	public ResponseEntity<String> calculerFactureMensuelle(Map<String,String> requestMap) {
        // Obtenez les données de recharge de l'utilisateur pour le mois et l'année donnés.
		
		try {
			System.out.println(requestMap);
			if(requestMap.containsKey("mois")  && requestMap.containsKey("annee") ) {
				if(requestMap.get("mois") != "" && requestMap.get("annee") != "" ) {
					LocalDate currentDate = LocalDate.now();
					int currentYear = currentDate.getYear();
					Month currentMonth = currentDate.getMonth();
					int currentMonthValue = currentMonth.getValue();
					if(Integer.parseInt(requestMap.get("annee")) > currentYear || (Integer.parseInt(requestMap.get("annee")) == currentYear && Integer.parseInt(requestMap.get("mois")) > currentMonthValue)) {
						return EtronPrjUtils.getResponseEntity("There is no facture yet for this date , please try a date before Today's date", HttpStatus.BAD_REQUEST);
					}else {
						User user = userrepos.findByEmail(jwtFilter.getCurrentuser());
						Facture f = facturerepos.getFactureByUserAndAnneeAndMois(user, Integer.parseInt(requestMap.get("mois")), Integer.parseInt(requestMap.get("annee")));
				        List<Recharge> recharges = rechargerepos.findByUserAndMoisAndAnnee(user, Integer.parseInt(requestMap.get("mois")), Integer.parseInt(requestMap.get("annee")));
				        
				        System.out.println(f + " " + recharges);
				        if (Objects.isNull(f)) {
				        	double montantTotal = 0.0;

					        for (Recharge recharge : recharges) {
					            double quantiteEnergie = recharge.getQuantiteEnergie(); // en kWh
					            String typeCharge = recharge.getTypeCharge(); // AC, DC/HPC, haute puissance
					            int dureeRechargeEnMinutes = recharge.getDureeRecharge();
					            
					            double coutRecharge = calculerCoutRecharge(quantiteEnergie, typeCharge, user.getContrat().getPlan().getType(), dureeRechargeEnMinutes);
					            montantTotal += coutRecharge;
					        }

					        LocalDate dateActuelle = LocalDate.now();
					        int jourActuel = dateActuelle.getDayOfMonth();
					        LocalDate dateDemandeFacture = LocalDate.of(Integer.parseInt(requestMap.get("annee")), Integer.parseInt(requestMap.get("mois")), jourActuel);
					        Facture facture = new Facture();
					        facture.setContrat(user.getContrat());
					        facture.setDateFacturation(dateDemandeFacture);
					        facture.setMontantTotal(montantTotal);
					        facture.setStatus("Impayée");
					        
					        facturerepos.save(facture);
					        
					        return EtronPrjUtils.getResponseEntity("Facture Successfully Registered", HttpStatus.OK);
				        } else {
				        	return EtronPrjUtils.getResponseEntity("Facture Already Exists", HttpStatus.BAD_REQUEST);
				        }
					}
					
				}else {
					return EtronPrjUtils.getResponseEntity("Missing Argument's Type Value", HttpStatus.BAD_REQUEST);
				}
			}else {
				return EtronPrjUtils.getResponseEntity("Missing Argument", HttpStatus.BAD_REQUEST);
			}
			
	        
		} catch (Exception e) {
			e.printStackTrace();
		}
		return EtronPrjUtils.getResponseEntity(EtronPrjConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
		
    }

	private double calculerCoutRecharge(double quantiteEnergie, String typeCharge, String typeAbonnement, int dureeRechargeEnMinutes) {
	    double cout = 0.0;
	    
	    /*LocalTime heureActuelle = LocalTime.now();
	    boolean estHeureBloquage = heureActuelle.isBefore(LocalTime.of(21, 0)) || heureActuelle.isAfter(LocalTime.of(9, 0));*/
	    
	    LocalTime heureActuelle = LocalTime.now();
	    LocalTime heureDebutBloquage = LocalTime.of(21, 0);
	    LocalTime heureFinBloquage = LocalTime.of(9, 0);

	    // Vérifiez si l'heure actuelle est en dehors de la plage horaire spécifiée (21h à 9h du jour suivant).
	    boolean estHeureBloquage = heureActuelle.isBefore(heureDebutBloquage) && heureActuelle.isAfter(heureFinBloquage);
	    
	    if (typeAbonnement.equals("basic")) {
	        if (typeCharge.equals("AC")) {
	            cout = quantiteEnergie * 0.50;
	            if (dureeRechargeEnMinutes > 240 && estHeureBloquage) {
	                cout += (dureeRechargeEnMinutes - 240) * 0.05;
	            }
	        } else if (typeCharge.equals("DC/HPC")) {
	            cout = quantiteEnergie * 0.74;
	            if (dureeRechargeEnMinutes > 90) {
	                cout += (dureeRechargeEnMinutes - 90) * 0.15;
	            }
	        } else if (typeCharge.equals("haute puissance")) {
	            cout = quantiteEnergie * 0.59;
	        }
	    } else if (typeAbonnement.equals("pro")) {
	        if (typeCharge.equals("AC")) {
	            cout = quantiteEnergie * 0.35;
	            if (dureeRechargeEnMinutes > 240 && estHeureBloquage) {
	                cout += (dureeRechargeEnMinutes - 240) * 0.05;
	            }
	        } else if (typeCharge.equals("DC/HPC")) {
	            cout = quantiteEnergie * 0.63;
	            if (dureeRechargeEnMinutes > 90 ) {
	                cout += (dureeRechargeEnMinutes - 90) * 0.15;
	            }
	        } else if (typeCharge.equals("haute puissance")) {
	            cout = quantiteEnergie * 0.50;
	        }
	    } else {
	        if (typeCharge.equals("AC")) {
	            cout = quantiteEnergie * 0.42;
	            if (dureeRechargeEnMinutes > 240 && estHeureBloquage) {
	                cout += (dureeRechargeEnMinutes - 240) * 0.05;
	            }
	        } else if (typeCharge.equals("DC/HPC")) {
	            cout = quantiteEnergie * 0.66;
	            if (dureeRechargeEnMinutes > 90) {
	                cout += (dureeRechargeEnMinutes - 90) * 0.15;
	            }
	        } else if (typeCharge.equals("haute puissance")) {
	            cout = quantiteEnergie * 0.59;
	        }
	    }

	    return cout;
	}


    // Méthode pour vérifier l'éligibilité à l'abonnement gratuit la première année
    public boolean estEligibleAbonnementGratuit(User user) {
    	
        LocalDate dateAchatEtron = user.getDateInscription();
        LocalDate dateExpirationAbonnementGratuit = dateAchatEtron.plusYears(1);
        // Si la date actuelle est antérieure à la date d'expiration de l'abonnement gratuit, l'utilisateur est éligible.
        return LocalDate.now().isBefore(dateExpirationAbonnementGratuit) && user.getModeleVoiture().equals(voiturerepos.getDernierModele());
    }
    
    public double calculerDistance(double lat1, double lon1, double lat2, double lon2) {
        // Rayon de la Terre en kilomètres
        double rayonTerre = 6371.0;

        // Conversion des coordonnées de degrés en radians
        double lat1Rad = Math.toRadians(lat1);
        double lon1Rad = Math.toRadians(lon1);
        double lat2Rad = Math.toRadians(lat2);
        double lon2Rad = Math.toRadians(lon2);

        // Différence de latitudes et de longitudes
        double deltaLat = lat2Rad - lat1Rad;
        double deltaLon = lon2Rad - lon1Rad;

        // Calcul de la distance en utilisant la formule haversine
        double a = Math.pow(Math.sin(deltaLat / 2), 2) + Math.cos(lat1Rad) * Math.cos(lat2Rad) * Math.pow(Math.sin(deltaLon / 2), 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = rayonTerre * c;

        return distance;
    }
    
    

}
