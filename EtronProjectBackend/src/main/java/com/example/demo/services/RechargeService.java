package com.example.demo.services;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.demo.JWT.JwtFilter;
import com.example.demo.constants.EtronPrjConstants;
import com.example.demo.models.Contrat;
import com.example.demo.models.Recharge;
import com.example.demo.models.User;
import com.example.demo.repositories.ContratRepository;
import com.example.demo.repositories.RechargeRepository;
import com.example.demo.repositories.UserRepository;
import com.example.demo.utils.EtronPrjUtils;
import com.example.demoDTO.RechargeDTO;

@Service
public class RechargeService {
	
	@Autowired
	RechargeRepository rechargerepos;
	
	@Autowired
	UserRepository userrepos;
	
	@Autowired
	ContratRepository contratrepos;
	
	@Autowired
	JwtFilter jwtFilter;
	
	//Ajout Recharge d'un User
    public Recharge getRechargeById(long id){
        return rechargerepos.findById(id).orElse(null);
    }

    public ResponseEntity<String> addRecharge(Map<String,String> requestMap){
        System.out.println("Inside Ajout Recharge :" + requestMap);
    	try {
    		User user = userrepos.findByEmail(jwtFilter.getCurrentuser());
    		Contrat newestContract = contratrepos.getNewestContratByUser(user.getId());
    		LocalDate currentDate = LocalDate.now();
            System.out.println("Inside Ajout Recharge :" + newestContract);

			// Check if the current date falls within the date range of the newest contract
			if (newestContract != null) {
			    LocalDate startDate = newestContract.getDateDebut();
			    LocalDate endDate = newestContract.getDateFin();
			    
			    // Check if the current date's year and month match the contract's start date
			    YearMonth currentYearMonth = YearMonth.from(currentDate);
			    YearMonth contractStartYearMonth = YearMonth.from(startDate);
			    
			    if (currentYearMonth.equals(contractStartYearMonth) && 
			        !currentDate.isBefore(startDate) && 
			        !currentDate.isAfter(endDate)) {
			    	if (requestMap.containsKey("quantiteEnergie") && requestMap.containsKey("typeCharge") && requestMap.containsKey("DureeRecharge") ) {
		            	if(!TestService.isInteger(requestMap.get("typeCharge")) && !requestMap.get("typeCharge").isEmpty()) {
		            		if(TestService.doublePositifNegatif(Double.parseDouble(requestMap.get("quantiteEnergie"))) && TestService.intPositifNegatif(Integer.parseInt(requestMap.get("DureeRecharge"))) ) {
		            			if(!requestMap.get("quantiteEnergie").isEmpty() && !requestMap.get("DureeRecharge").isEmpty()) {
		            				
		            				Recharge recharge = new Recharge();
		                        	recharge.setQuantiteEnergie(Double.parseDouble(requestMap.get("quantiteEnergie")));
		                        	recharge.setTypeCharge(requestMap.get("typeCharge"));
		                        	
		                        	/*DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

		                        	LocalDateTime parsedDate = LocalDateTime.parse(requestMap.get("dateHeureRecharge"), formatter);*/
		                        	recharge.setDateHeureRecharge(LocalDateTime.now());
		                        	
		                        	recharge.setDureeRecharge(Integer.parseInt(requestMap.get("DureeRecharge")));
		                        	
		                        	recharge.setUser(user);
		                        	rechargerepos.save(recharge);
		                            return EtronPrjUtils.getResponseEntity("Recharge Successfully Registered", HttpStatus.OK);
		            			}else {
		            				return EtronPrjUtils.getResponseEntity("Missing attribut", HttpStatus.BAD_REQUEST);
		            			}
		            			
		            		}else {
		            			return EtronPrjUtils.getResponseEntity("Negative Numbers Not Supported , Please Give a Positive Number", HttpStatus.BAD_REQUEST);
		            		}
		            	}else {
		            		return EtronPrjUtils.getResponseEntity("Typecharge is missing", HttpStatus.BAD_REQUEST);
		            	}	
		                
		            } else {
		                return EtronPrjUtils.getResponseEntity(EtronPrjConstants.INVALID_DATA, HttpStatus.BAD_REQUEST);
		            }
			    }else {
			    	return EtronPrjUtils.getResponseEntity("Your not linked to a plan", HttpStatus.BAD_REQUEST);
			    }
			}else {
				return EtronPrjUtils.getResponseEntity("Your not linked to a plan", HttpStatus.BAD_REQUEST);
		    }
  
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return EtronPrjUtils.getResponseEntity(EtronPrjConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
    public List<Recharge> getRechargeByUserAndMoisAndAnnee(int idUser , int mois, int annee){
    	User user = userrepos.findById(idUser);
    	return rechargerepos.findByUserAndMoisAndAnnee(user, mois, annee);
    }
    
    public ResponseEntity<List<RechargeDTO>> getRecharges() {
        try {
            List<Recharge> recharges = rechargerepos.getRechargesByUser(jwtFilter.getCurrentuser());
            List<RechargeDTO> rechargeDTOs = new ArrayList<>();

            for (Recharge recharge : recharges) {
                RechargeDTO rechargeDTO = new RechargeDTO();

                // Map data from Recharge to RechargeDTO
                rechargeDTO.setQuantiteEnergie(recharge.getQuantiteEnergie());
                rechargeDTO.setTypeCharge(recharge.getTypeCharge());
                rechargeDTO.setDateHeureRecharge(recharge.getDateHeureRecharge());
                rechargeDTO.setDureeRecharge(recharge.getDureeRecharge());

                // Include the email of the associated User
                rechargeDTO.setUserEmail(recharge.getUser().getEmail());

                rechargeDTOs.add(rechargeDTO);
            }

            return new ResponseEntity<>(rechargeDTOs, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
