package com.example.demo.services;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.demo.JWT.JwtFilter;
import com.example.demo.constants.EtronPrjConstants;
import com.example.demo.models.Recharge;
import com.example.demo.models.User;
import com.example.demo.repositories.RechargeRepository;
import com.example.demo.repositories.UserRepository;
import com.example.demo.utils.EtronPrjUtils;

@Service
public class RechargeService {
	
	@Autowired
	RechargeRepository rechargerepos;
	
	@Autowired
	UserRepository userrepos;
	
	@Autowired
	JwtFilter jwtFilter;
	
	//Ajout Recharge d'un User
    public Recharge getRechargeById(long id){
        return rechargerepos.findById(id).orElse(null);
    }

    public ResponseEntity<String> addRecharge(Map<String,String> requestMap){
        System.out.println("Inside Ajout Recharge :" + requestMap);
    	try {
            if (requestMap.containsKey("quantiteEnergie") && requestMap.containsKey("typeCharge") && requestMap.containsKey("DureeRecharge")) {
            	Recharge recharge = new Recharge();
            	recharge.setQuantiteEnergie(Double.parseDouble(requestMap.get("quantiteEnergie")));
            	recharge.setTypeCharge(requestMap.get("typeCharge"));
            	
            	/*DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

            	LocalDateTime parsedDate = LocalDateTime.parse(requestMap.get("dateHeureRecharge"), formatter);*/
            	recharge.setDateHeureRecharge(LocalDateTime.now());
            	
            	recharge.setDureeRecharge(Integer.parseInt(requestMap.get("DureeRecharge")));
            	rechargerepos.save(recharge);
                return EtronPrjUtils.getResponseEntity("Recharge Successfully Registered", HttpStatus.OK);
                	
                
            } else {
                return EtronPrjUtils.getResponseEntity(EtronPrjConstants.INVALID_DATA, HttpStatus.BAD_REQUEST);
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
    
    public ResponseEntity<List<Recharge>> getRecharges(){
		try {
			List<Recharge> recharges = rechargerepos.getRechargesByUser(jwtFilter.getCurrentuser());
			return new ResponseEntity<>(recharges, HttpStatus.OK);
		} catch(Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
