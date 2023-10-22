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
import com.example.demoDTO.BorneRechargeDTO;

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
    		if(jwtFilter.isAdmin()) {
    			if (requestMap.containsKey("typecharge") && requestMap.containsKey("latitude") && requestMap.containsKey("longitude") && requestMap.containsKey("disponible")) {
                	if(requestMap.get("typecharge") != "" && !TestService.isInteger(requestMap.get("typecharge")) ) {
                		if(!requestMap.get("latitude").isEmpty() && !requestMap.get("longitude").isEmpty() && !requestMap.get("disponible").isEmpty()) {
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
                		}else {
                			return EtronPrjUtils.getResponseEntity("Missing Argument's Value", HttpStatus.BAD_REQUEST);
                		}
                	}else {
                		return EtronPrjUtils.getResponseEntity("Type is missing", HttpStatus.BAD_REQUEST);
                	}
                	
                } else {
                    return EtronPrjUtils.getResponseEntity(EtronPrjConstants.INVALID_DATA, HttpStatus.BAD_REQUEST);
                }
    		}else {
    			return EtronPrjUtils.getResponseEntity(EtronPrjConstants.UNAUTHORIZED_ACCESS, HttpStatus.UNAUTHORIZED);
    		}
            
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return EtronPrjUtils.getResponseEntity(EtronPrjConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }
	
    public ResponseEntity<String> update(Map<String, String> requestMap) {
        try {
            if (jwtFilter.isAdmin()) {
                if (
                    requestMap.containsKey("typecharge") && !requestMap.get("typecharge").isEmpty() &&
                    requestMap.containsKey("latitude") && TestService.isNumeric(requestMap.get("latitude")) &&
                    requestMap.containsKey("longitude") && TestService.isNumeric(requestMap.get("longitude")) &&
                    requestMap.containsKey("disponible") && !requestMap.get("disponible").isEmpty()) {
                        BorneRecharge borne = bornerepos.getBorneByLongitudeAndLatitude(Double.parseDouble(requestMap.get("latitude")),Double.parseDouble(requestMap.get("longitude")));
                        //BorneRecharge borne = bornerepos.findById((Integer.parseInt(requestMap.get("latitude"))));
                        System.out.println(borne);
                        if (borne != null) {
                            borne.setTypecharge(requestMap.get("typecharge"));
                            borne.setLatitude(Double.parseDouble(requestMap.get("latitude")));
                            borne.setLongitude(Double.parseDouble(requestMap.get("longitude")));
                            borne.setDisponible(Boolean.parseBoolean(requestMap.get("disponible")));
                            bornerepos.save(borne);
                            return EtronPrjUtils.getResponseEntity(EtronPrjConstants.USER_STATUS, HttpStatus.OK);
                        } else {
                            return EtronPrjUtils.getResponseEntity("BorneRecharge id doesn't exist.", HttpStatus.OK);
                        }
                    
                } else {
                    return EtronPrjUtils.getResponseEntity("Missing or invalid argument values", HttpStatus.BAD_REQUEST);
                }
            } else {
                return EtronPrjUtils.getResponseEntity(EtronPrjConstants.UNAUTHORIZED_ACCESS, HttpStatus.UNAUTHORIZED);
            }
        } catch (NumberFormatException e) {
            // Handle the NumberFormatException (e.g., invalid numeric input)
            return EtronPrjUtils.getResponseEntity("Invalid input format for numeric values.", HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            // Handle other exceptions
            e.printStackTrace();
        }
        return EtronPrjUtils.getResponseEntity(EtronPrjConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }


	
	public ResponseEntity<String> deleteBorne(int idBorne){
		try {
			if(jwtFilter.isAdmin()) {
				if(TestService.intPositifNegatif(idBorne)) {
					BorneRecharge borne = bornerepos.findById(idBorne);
					bornerepos.delete(borne);
					return EtronPrjUtils.getResponseEntity("BorneRecharge Deleted Successfully", HttpStatus.OK);
				}else {
					return EtronPrjUtils.getResponseEntity("Negative Numbers Not Supported , Please Give a Positive Number", HttpStatus.BAD_REQUEST);
				}
				
				
			}else {
				return EtronPrjUtils.getResponseEntity(EtronPrjConstants.UNAUTHORIZED_ACCESS, HttpStatus.UNAUTHORIZED);
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		return EtronPrjUtils.getResponseEntity(EtronPrjConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	public ResponseEntity<List<BorneRechargeDTO>> getBorneRecharges() {
	    try {
	        List<BorneRecharge> borneRecharges = bornerepos.findAll();
	        List<BorneRechargeDTO> borneRechargeDTOs = new ArrayList<>();

	        for (BorneRecharge borneRecharge : borneRecharges) {
	            BorneRechargeDTO borneRechargeDTO = new BorneRechargeDTO();

	            // Map data from BorneRecharge to BorneRechargeDTO
	            borneRechargeDTO.setIdBorne(borneRecharge.getId());
	            borneRechargeDTO.setTypecharge(borneRecharge.getTypecharge());
	            borneRechargeDTO.setLatitude(borneRecharge.getLatitude());
	            borneRechargeDTO.setLongitude(borneRecharge.getLongitude());
	            borneRechargeDTO.setDisponible(borneRecharge.isDisponible());

	            borneRechargeDTOs.add(borneRechargeDTO);
	        }

	        return new ResponseEntity<>(borneRechargeDTOs, HttpStatus.OK);
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
