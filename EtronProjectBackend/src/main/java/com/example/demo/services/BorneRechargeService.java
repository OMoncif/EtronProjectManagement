package com.example.demo.services;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Service;

import com.example.demo.JWT.CustomerUserDetailsService;
import com.example.demo.JWT.JwtFilter;
import com.example.demo.JWT.JwtUtil;
import com.example.demo.constants.EtronPrjConstants;
import com.example.demo.models.BorneRecharge;
import com.example.demo.repositories.BorneRechargeRepository;
import com.example.demo.utils.EmailUtils;
import com.example.demo.utils.EtronPrjUtils;

@Service
public class BorneRechargeService {
	
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
	private BorneRechargeRepository bornerepos;
	
	
	//BorneRecharge
    public ResponseEntity<String> addBorneRecharge(Map<String,String> requestMap){
        System.out.println("Inside Ajout BorneRecharge :" + requestMap);
    	try {
            if (requestMap.containsKey("typecharge") && requestMap.containsKey("latitude") && requestMap.containsKey("longitude") && requestMap.containsKey("disponible")) {
            	BorneRecharge bornerecharge = bornerepos.getBorneByLongitudeAndLatitude(Double.parseDouble(requestMap.get("latitude")),Double.parseDouble(requestMap.get("longitude")));
            	if(Objects.isNull(bornerecharge)) {
            		BorneRecharge borne = new BorneRecharge();
                	borne.setTypecharge(requestMap.get("typecharge"));
                	borne.setDisponible(Boolean.parseBoolean(requestMap.get("disponible")));
                	borne.setLatitude(Double.parseDouble(requestMap.get("latitude")));
                	borne.setLongitude(Double.parseDouble(requestMap.get("longitude")));

                	bornerepos.save(borne);
                    return EtronPrjUtils.getResponseEntity("BornRecharge Successfully Registered", HttpStatus.OK);
            	} else {
            		return EtronPrjUtils.getResponseEntity("Borne already exists", HttpStatus.BAD_REQUEST);
            	}
                
            } else {
                return EtronPrjUtils.getResponseEntity(EtronPrjConstants.INVALID_DATA, HttpStatus.BAD_REQUEST);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return EtronPrjUtils.getResponseEntity(EtronPrjConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }
	
	public ResponseEntity<String> update(Map<String, String> requestMap) {
		try {
			if(jwtFilter.isAdmin()) {
				BorneRecharge borne = bornerepos.findById(Integer.parseInt(requestMap.get("idBorne")));
				if(borne != null) {
					borne.setTypecharge(requestMap.get("typecharge"));
					borne.setLatitude(Double.parseDouble(requestMap.get("latitude")));
					borne.setLongitude(Double.parseDouble(requestMap.get("longitude")));
					borne.setDisponible(Boolean.parseBoolean(requestMap.get("disponible")));
					bornerepos.save(borne);
					return EtronPrjUtils.getResponseEntity(EtronPrjConstants.USER_STATUS, HttpStatus.OK);
				}
				else {
					return EtronPrjUtils.getResponseEntity("BorneRecharge id doesn't exist.", HttpStatus.OK);
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
	
	public ResponseEntity<String> deleteBorne(int idBorne){
		try {
			if(jwtFilter.isAdmin()) {
				
				BorneRecharge borne = bornerepos.findById(idBorne);
				bornerepos.delete(borne);
				return EtronPrjUtils.getResponseEntity("BorneRecharge Deleted Successfully", HttpStatus.OK);
			}else {
				return EtronPrjUtils.getResponseEntity(EtronPrjConstants.UNAUTHORIZED_ACCESS, HttpStatus.UNAUTHORIZED);
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		return EtronPrjUtils.getResponseEntity(EtronPrjConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	public ResponseEntity<List<BorneRecharge>> getBornes(){
		try {
			List<BorneRecharge> bornes = bornerepos.findAll();
			return new ResponseEntity<>(bornes, HttpStatus.OK);
		} catch(Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
