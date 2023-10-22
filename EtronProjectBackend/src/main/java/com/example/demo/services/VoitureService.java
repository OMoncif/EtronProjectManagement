package com.example.demo.services;

import java.time.LocalDate;
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
import com.example.demo.models.Voiture;
import com.example.demo.repositories.VoitureRepository;
import com.example.demo.utils.EmailUtils;
import com.example.demo.utils.EtronPrjUtils;

@Service
public class VoitureService {

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
	private VoitureRepository carrepos;
	
	//Ajout de Voiture AUDI
    public ResponseEntity<String> addVoiture(Map<String, String> requestMap){
        System.out.println("Inside Ajout Voiture:" + requestMap);
    	try {
            if (requestMap.containsKey("modele")) {
            	if(!TestService.isInteger(requestMap.get("modele"))) {
            		if(!requestMap.get("modele").isEmpty()) {
            			Voiture voiture = carrepos.findByModele(requestMap.get("modele"));
                        if (Objects.isNull(voiture)) {
                        	Voiture car = new Voiture();
                        	car.setModele(requestMap.get("modele"));
                        	car.setDateAjoutVoiture(LocalDate.now());
                        	carrepos.save(car);
                            return EtronPrjUtils.getResponseEntity("Car Successfully Registered", HttpStatus.OK);
                        } else {
                            return EtronPrjUtils.getResponseEntity("Car already exists", HttpStatus.BAD_REQUEST);
                        }
            		}else {
            			return EtronPrjUtils.getResponseEntity("Missing Modele Value", HttpStatus.BAD_REQUEST);
            		}
            		
            	}else {
            		return EtronPrjUtils.getResponseEntity("Please Give a String Value to modele ", HttpStatus.BAD_REQUEST);
            	}
                
            } else {
                return EtronPrjUtils.getResponseEntity(EtronPrjConstants.INVALID_DATA, HttpStatus.BAD_REQUEST);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return EtronPrjUtils.getResponseEntity(EtronPrjConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }
	
	public ResponseEntity<List<Voiture>> getVoitures(){
		try {
			if(jwtFilter.isAdmin()) {
				List<Voiture> cars = carrepos.findAll();
				return new ResponseEntity<>(cars, HttpStatus.OK);
			}else {
				return new ResponseEntity<>(new ArrayList<>(), HttpStatus.UNAUTHORIZED);
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	public ResponseEntity<String> update(Map<String, String> requestMap) {
		try {
			if(jwtFilter.isAdmin()) {
				if(TestService.intPositifNegatif(Integer.parseInt(requestMap.get("idVoiture")))) {
					if(!TestService.isInteger(requestMap.get("modele"))) {
						if(!requestMap.get("modele").isEmpty()) {
							Voiture car = carrepos.findById(Integer.parseInt(requestMap.get("idVoiture")));
							if(car != null) {
								car.setModele(requestMap.get("modele"));
								car.setDateAjoutVoiture(LocalDate.parse(requestMap.get("dateAjoutVoiture")));
								carrepos.save(car);
								return EtronPrjUtils.getResponseEntity(EtronPrjConstants.USER_STATUS, HttpStatus.OK);
							}
							else {
								return EtronPrjUtils.getResponseEntity("Car id doesn't exist.", HttpStatus.OK);
							}
						}else {
							return EtronPrjUtils.getResponseEntity("Missing Modele Value", HttpStatus.BAD_REQUEST);
						}
						
					}else {
						return EtronPrjUtils.getResponseEntity("Please Give a String Value to modele", HttpStatus.BAD_REQUEST);
					}
				}else {
					return EtronPrjUtils.getResponseEntity("idVoiture Must be a Positive Number", HttpStatus.BAD_REQUEST);
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
	
	public ResponseEntity<String> deleteCar(int idVoiture){
		try {
			if(jwtFilter.isAdmin()) {
				if(TestService.intPositifNegatif(idVoiture)) {
					Voiture car = carrepos.findById(idVoiture);
					carrepos.delete(car);
					return EtronPrjUtils.getResponseEntity("Car Deleted Successfully", HttpStatus.OK);
				}else {
					return EtronPrjUtils.getResponseEntity("IdVoiture must be Positive and not Negative or Zero", HttpStatus.BAD_REQUEST);
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
