package com.example.demo.services;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Service;

import com.example.demo.JWT.CustomerUserDetailsService;
import com.example.demo.JWT.JwtFilter;
import com.example.demo.JWT.JwtUtil;
import com.example.demo.constants.EtronPrjConstants;
import com.example.demo.models.AbonnementPlan;
import com.example.demo.models.Contrat;
import com.example.demo.models.User;
import com.example.demo.repositories.AbonnementPlanRepository;
import com.example.demo.repositories.ContratRepository;
import com.example.demo.repositories.UserRepository;
import com.example.demo.utils.EmailUtils;
import com.example.demo.utils.EtronPrjUtils;
import com.example.demoDTO.AbonnementPlanDTO;

@Service
public class AbonnementPlanService {
	
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
	private AbonnementPlanRepository planrepos;
	
	@Autowired
	private ContratRepository contratrepos;
	
	@Autowired
	private UserRepository userrepos;
	
	
	//Ajout Un Abonnement 
    public ResponseEntity<String> addAbonnement(Map<String,String> requestMap){        
        System.out.println("Inside Ajout Abonnement:" + requestMap);
    	try {
    		if(jwtFilter.isAdmin()) {
    			if (requestMap.containsKey("type") && requestMap.containsKey("fraismois") && requestMap.containsKey("fraisAC") && requestMap.containsKey("fraisDCHPC") && requestMap.containsKey("fraisHautePuissance") && requestMap.containsKey("fraisBlocageAC") && requestMap.containsKey("fraisBlocageDC") ) {
    				if(requestMap.get("type") != "" && !TestService.isInteger(requestMap.get("type")) ) {
    					if(requestMap.get("fraismois") != "" && requestMap.get("fraisAC") != "" && requestMap.get("fraisDCHPC") != "" && requestMap.get("fraisHautePuissance") != "" && requestMap.get("fraisBlocageAC") != "" && requestMap.get("fraisBlocageDC") != "" ) {
    						if(TestService.doublePositifNegatif(Double.parseDouble(requestMap.get("fraismois"))) && TestService.doublePositifNegatif(Double.parseDouble(requestMap.get("fraisAC"))) && TestService.doublePositifNegatif(Double.parseDouble(requestMap.get("fraisDCHPC"))) && TestService.doublePositifNegatif(Double.parseDouble(requestMap.get("fraisHautePuissance"))) && TestService.doublePositifNegatif(Double.parseDouble(requestMap.get("fraisBlocageAC"))) && TestService.doublePositifNegatif(Double.parseDouble(requestMap.get("fraisBlocageDC"))) ) {
    							AbonnementPlan plan  = planrepos.findByType(requestMap.get("type"));
    	     	                   
                                if (Objects.isNull(plan)) {
                                	AbonnementPlan abonnement = new AbonnementPlan(); 
                                	abonnement.setType(requestMap.get("type"));
                                	abonnement.setFraismois(Double.parseDouble(requestMap.get("fraismois")));
                                	abonnement.setDureeContrat(1);
                                	abonnement.setFraisAC(Double.parseDouble(requestMap.get("fraisAC")));
                                	abonnement.setFraisDCHPC(Double.parseDouble(requestMap.get("fraisDCHPC")));
                                	abonnement.setFraisHautePuissance(Double.parseDouble(requestMap.get("fraisHautePuissance")));
                                	abonnement.setFraisBlocageAC(Double.parseDouble(requestMap.get("fraisBlocageAC")));
                                	abonnement.setFraisBlocageDC(Double.parseDouble(requestMap.get("fraisBlocageDC")));
                                	
                                	planrepos.save(abonnement);
                                    return EtronPrjUtils.getResponseEntity("Abonnement Successfully Registered", HttpStatus.OK);
                                } else {
                                    return EtronPrjUtils.getResponseEntity("Abonnement already exists", HttpStatus.BAD_REQUEST);
                                }
    						}else {
    							return EtronPrjUtils.getResponseEntity("Negative Numbers Not Supported , Please Give a Positive Number", HttpStatus.BAD_REQUEST);
    						}
    						
    					}else {
    						return EtronPrjUtils.getResponseEntity("Missing Argument's Type Value", HttpStatus.BAD_REQUEST);
    					}
    					
    				}else {
    					return EtronPrjUtils.getResponseEntity("Type is missing", HttpStatus.BAD_REQUEST);
    				}
    				
                } else {
                    return EtronPrjUtils.getResponseEntity(EtronPrjConstants.INVALID_DATA, HttpStatus.BAD_REQUEST);
                }
    		} else {
    			return EtronPrjUtils.getResponseEntity(EtronPrjConstants.UNAUTHORIZED_ACCESS, HttpStatus.UNAUTHORIZED);
    		}
            
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return EtronPrjUtils.getResponseEntity(EtronPrjConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    //Annuler Abonnement 
    public ResponseEntity<String> annulerAbonnement(Map<String,String> requestMap) {
        try {
        	if(TestService.intPositifNegatif(Integer.parseInt(requestMap.get("userId"))) && requestMap.get("userId")!="") {
        		User client = userrepos.findById(Integer.parseInt(requestMap.get("userId")));
                
                if (client != null) {
                	List<Contrat> contrats = contratrepos.getOldestContratByUser(client, PageRequest.of(0, 1));
                	Contrat oldContrat = contrats.get(0);
                    LocalDate datePremierContrat = oldContrat.getDateDebut();
                    LocalDate dateActuelle = LocalDate.now();
                    
                    // Calcul de la durée en mois
                    long dureeEnMois = ChronoUnit.MONTHS.between(datePremierContrat, dateActuelle);
                    
                    if (dureeEnMois >= 12) {
                        // Le client peut annuler son abonnement
                    	oldContrat.setUser(null); // Annule l'abonnement
                    	contratrepos.save(oldContrat);
                        return EtronPrjUtils.getResponseEntity("Abonnements annulés avec succès", HttpStatus.OK);
                    } else {
                        // Le client ne peut pas annuler son abonnement
                        return EtronPrjUtils.getResponseEntity("Impossible d'annuler l'abonnement (moins de 12 mois d'ancienneté)", HttpStatus.BAD_REQUEST);
                    }
                } else {
                    return EtronPrjUtils.getResponseEntity("Client non trouvé", HttpStatus.NOT_FOUND);
                }
        	}else {
        		return EtronPrjUtils.getResponseEntity("Negative Aregument's Value Not Supported", HttpStatus.BAD_REQUEST);
        	}
        	
        }catch(Exception e) {
        	e.printStackTrace();
        }
        return EtronPrjUtils.getResponseEntity(EtronPrjConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    	
    }
	
    public ResponseEntity<List<AbonnementPlanDTO>> getAbonnementPlans() {
        try {

                List<AbonnementPlan> abonnementPlans = planrepos.findAll();
                List<AbonnementPlanDTO> abonnementPlanDTOs = new ArrayList<>();

                for (AbonnementPlan abonnementPlan : abonnementPlans) {
                    AbonnementPlanDTO abonnementPlanDTO = new AbonnementPlanDTO();

                    // Map abonnement plan data to the DTO
                    //abonnementPlanDTO.setId(abonnementPlan.getId());
                    abonnementPlanDTO.setType(abonnementPlan.getType());
                    abonnementPlanDTO.setFraismois(abonnementPlan.getFraismois());
                    abonnementPlanDTO.setDureeContrat(abonnementPlan.getDureeContrat());
                    abonnementPlanDTO.setFraisAC(abonnementPlan.getFraisAC());
                    abonnementPlanDTO.setFraisDCHPC(abonnementPlan.getFraisDCHPC());
                    abonnementPlanDTO.setFraisHautePuissance(abonnementPlan.getFraisHautePuissance());
                    abonnementPlanDTO.setFraisBlocageAC(abonnementPlan.getFraisBlocageAC());
                    abonnementPlanDTO.setFraisBlocageDC(abonnementPlan.getFraisBlocageDC());

                    abonnementPlanDTOs.add(abonnementPlanDTO);
                }

                return new ResponseEntity<>(abonnementPlanDTOs, HttpStatus.OK);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
	
    public ResponseEntity<String> update(Map<String, String> requestMap) {
        try {
            System.out.println(requestMap);
            if (jwtFilter.isAdmin()) {
                if (
                    requestMap.containsKey("type") && !requestMap.get("type").isEmpty() &&
                    requestMap.containsKey("fraismois") && !requestMap.get("fraismois").isEmpty() &&
                    requestMap.containsKey("dureeContrat") && !requestMap.get("dureeContrat").isEmpty() &&
                    requestMap.containsKey("fraisAC") && !requestMap.get("fraisAC").isEmpty() &&
                    requestMap.containsKey("fraisDCHPC") && !requestMap.get("fraisDCHPC").isEmpty() &&
                    requestMap.containsKey("fraisHautePuissance") && !requestMap.get("fraisHautePuissance").isEmpty() &&
                    requestMap.containsKey("fraisBlocageAC") && !requestMap.get("fraisBlocageAC").isEmpty() &&
                    requestMap.containsKey("fraisBlocageDC") && !requestMap.get("fraisBlocageDC").isEmpty()) {


                    AbonnementPlan plan = planrepos.findByType(requestMap.get("type"));

                    if (plan != null) {
                        plan.setType(requestMap.get("type"));
                        plan.setFraismois(Double.parseDouble(requestMap.get("fraismois")));
                        plan.setDureeContrat(Integer.parseInt(requestMap.get("dureeContrat")));
                        plan.setFraisAC(Double.parseDouble(requestMap.get("fraisAC")));
                        plan.setFraisDCHPC(Double.parseDouble(requestMap.get("fraisDCHPC")));
                        plan.setFraisHautePuissance(Double.parseDouble(requestMap.get("fraisHautePuissance")));
                        plan.setFraisBlocageAC(Double.parseDouble(requestMap.get("fraisBlocageAC")));
                        plan.setFraisBlocageDC(Double.parseDouble(requestMap.get("fraisBlocageDC")));
                        planrepos.save(plan);
                        return EtronPrjUtils.getResponseEntity(EtronPrjConstants.USER_STATUS, HttpStatus.OK);
                    } else {
                        return EtronPrjUtils.getResponseEntity("AbonnementPlan with given id doesn't exist.", HttpStatus.OK);
                    }
                } else {
                    return EtronPrjUtils.getResponseEntity("Missing Argument's Value", HttpStatus.BAD_REQUEST);
                }
            } else {
                return EtronPrjUtils.getResponseEntity(EtronPrjConstants.UNAUTHORIZED_ACCESS, HttpStatus.UNAUTHORIZED);
            }
        } catch (NumberFormatException e) {
            // Handle the NumberFormatException (e.g., invalid numeric input)
            String invalidInput = e.getMessage();
            e.printStackTrace();
            return EtronPrjUtils.getResponseEntity("Invalid input format for numeric values: " + invalidInput, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            // Handle other exceptions
            e.printStackTrace();
        }
        return EtronPrjUtils.getResponseEntity(EtronPrjConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

				
				
			
	
	
	public ResponseEntity<String> deletePlan(String type){
		try {
			if(jwtFilter.isAdmin()) {
				if(!TestService.isInteger(type)) {
					AbonnementPlan plan = planrepos.findByType(type);
					planrepos.delete(plan);
					return EtronPrjUtils.getResponseEntity("Abonnement Deleted Successfully", HttpStatus.OK);
				}else {
					return EtronPrjUtils.getResponseEntity("Numbers Not Supported , Please Give a String value", HttpStatus.BAD_REQUEST);
				}
			}else {
				return EtronPrjUtils.getResponseEntity(EtronPrjConstants.UNAUTHORIZED_ACCESS, HttpStatus.UNAUTHORIZED);
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		return EtronPrjUtils.getResponseEntity(EtronPrjConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
