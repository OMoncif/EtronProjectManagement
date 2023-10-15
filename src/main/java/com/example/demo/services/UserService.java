package com.example.demo.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Service;

import com.example.demo.JWT.CustomerUserDetailsService;
import com.example.demo.JWT.JwtFilter;
import com.example.demo.JWT.JwtUtil;
import com.example.demo.constants.EtronPrjConstants;
import com.example.demo.models.User;
import com.example.demo.repositories.UserRepository;
import com.example.demo.utils.EmailUtils;
import com.example.demo.utils.EtronPrjUtils;


@Service
public class UserService {
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
	
	public ResponseEntity<List<User>> getUsers(){
		try {
			if(jwtFilter.isAdmin()) {
				List<User> users = userrepos.findAll();
				return new ResponseEntity<>(users, HttpStatus.OK);
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
				User user = userrepos.findById(Integer.parseInt(requestMap.get("id")));
				if(user != null) {
					user.setName(requestMap.get("name"));
					user.setPrenom(requestMap.get("prenom"));
					user.setAdresse(requestMap.get("adresse"));
					user.setContactnumber(requestMap.get("contactnumber"));
					user.setRole(requestMap.get("role"));
					user.setModeleVoiture(requestMap.get("modeleVoiture"));
					user.setEmail(requestMap.get("email"));
					//user.setDateInscription(LocalDate.parse(requestMap.get("dateInscritpion")));
					userrepos.save(user);
					return EtronPrjUtils.getResponseEntity(EtronPrjConstants.USER_STATUS, HttpStatus.OK);
				}
				else {
					return EtronPrjUtils.getResponseEntity("User id doesn't exist.", HttpStatus.OK);
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
	
	public ResponseEntity<String> deleteUser(int idUser){
		try {
			if(jwtFilter.isAdmin()) {
				
				User user = userrepos.findById(idUser);
				userrepos.delete(user);
				return EtronPrjUtils.getResponseEntity("User Deleted Successfully", HttpStatus.OK);
			}else {
				return EtronPrjUtils.getResponseEntity(EtronPrjConstants.UNAUTHORIZED_ACCESS, HttpStatus.UNAUTHORIZED);
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		return EtronPrjUtils.getResponseEntity(EtronPrjConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
