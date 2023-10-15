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
    			if (requestMap.containsKey("type") && requestMap.containsKey("fraismois") && requestMap.containsKey("fraisAC") && requestMap.containsKey("fraisDCHPC") && requestMap.containsKey("fraisHautePuissance") && requestMap.containsKey("fraisBlocageAC") && requestMap.containsKey("fraisBlocageDC") && requestMap.containsKey("blocageACHeureCreuse")) {
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
                        return EtronPrjUtils.getResponseEntity("Model already exists", HttpStatus.BAD_REQUEST);
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
    }
	
	public ResponseEntity<List<AbonnementPlan>> getAbonnements(){
		try {
			List<AbonnementPlan> plans = planrepos.findAll();
			return new ResponseEntity<>(plans, HttpStatus.OK);
		} catch(Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	public ResponseEntity<String> update(Map<String, String> requestMap) {
		try {
			if(jwtFilter.isAdmin()) {
				AbonnementPlan plan = planrepos.findById(Integer.parseInt(requestMap.get("idPlan")));
				if(plan != null) {
					plan.setFraismois(Double.parseDouble(requestMap.get("fraismois")));
					plan.setType(requestMap.get("type"));
					plan.setDureeContrat(Integer.parseInt(requestMap.get("dureeContrat")));
					plan.setFraisAC(Double.parseDouble(requestMap.get("fraisAC")));
					plan.setFraisDCHPC(Double.parseDouble(requestMap.get("fraisDCHPC")));
					plan.setFraisHautePuissance(Double.parseDouble(requestMap.get("fraisHautePuissance")));
					plan.setFraisBlocageAC(Double.parseDouble(requestMap.get("fraisBlocageAC")));
					plan.setFraisBlocageDC(Double.parseDouble(requestMap.get("fraisBlocageDC")));
					planrepos.save(plan);
					return EtronPrjUtils.getResponseEntity(EtronPrjConstants.USER_STATUS, HttpStatus.OK);
				}
				else {
					return EtronPrjUtils.getResponseEntity("Plan id doesn't exist.", HttpStatus.OK);
				}
			}
			else {
				return EtronPrjUtils.getResponseEntity(EtronPrjConstants.UNAUTHORIZED_ACCESS, HttpStatus.UNAUTHORIZED);
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return EtronPrjUtils.getResponseEntity(EtronPrjConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	
	public ResponseEntity<String> deletePlan(int idPlan){
		try {
			if(jwtFilter.isAdmin()) {
				
				AbonnementPlan plan = planrepos.findById(idPlan);
				planrepos.delete(plan);
				return EtronPrjUtils.getResponseEntity("Plan Deleted Successfully", HttpStatus.OK);
			}else {
				return EtronPrjUtils.getResponseEntity(EtronPrjConstants.UNAUTHORIZED_ACCESS, HttpStatus.UNAUTHORIZED);
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		return EtronPrjUtils.getResponseEntity(EtronPrjConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
