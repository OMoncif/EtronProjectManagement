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
import com.example.demoDTO.UserDTO;


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
	
	/*public ResponseEntity<List<User>> getUsers(){
		try {
	        if (jwtFilter.isAdmin()) {
	            List<User> users = userrepos.findAll();

	            // Iterate through the list and break circular references
	            for (User user : users) {
	                user.setContrat(null); // Break the Contrat reference
	                user.setPaiements(null); // Break the Paiements reference
	                user.setBornes(null); // Break the Bornes reference
	                user.setRecharges(null); // Break the Recharges reference
	            }

	            System.out.println(users.get(0));

	            return new ResponseEntity<>(users, HttpStatus.OK);
	        } else {
	            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.UNAUTHORIZED);
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
		return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
	}*/
	public ResponseEntity<List<UserDTO>> getUsers() {
	    try {
	        if (jwtFilter.isAdmin()) {
	            List<User> users = userrepos.findAll();
	            List<UserDTO> userDTOs = new ArrayList<>();

	            for (User user : users) {
	                UserDTO userDTO = new UserDTO();

	                // Map user data to the DTO
	                userDTO.setName(user.getName());
	                userDTO.setPrenom(user.getPrenom());
	                userDTO.setEmail(user.getEmail());
	                userDTO.setContactnumber(user.getContactnumber());
	                userDTO.setAdresse(user.getAdresse());
	                userDTO.setRole(user.getRole());
	                userDTO.setModeleVoiture(user.getModeleVoiture());
	                userDTO.setDateInscription(user.getDateInscription());

	                userDTOs.add(userDTO);
	            }

	            return new ResponseEntity<>(userDTOs, HttpStatus.OK);
	        } else {
	            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.UNAUTHORIZED);
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	public ResponseEntity<String> update(Map<String, String> requestMap) {
		try {
			if(jwtFilter.isAdmin()) {
				if(TestService.intPositifNegatif(Integer.parseInt(requestMap.get("id")) )){
					if(!requestMap.get("id").isEmpty()) {
						if(!requestMap.get("name").isEmpty() && !requestMap.get("prenom").isEmpty() && !requestMap.get("adresse").isEmpty() && !requestMap.get("contactnumber").isEmpty() && !requestMap.get("role").isEmpty() && !requestMap.get("modeleVoiture").isEmpty() && !requestMap.get("email").isEmpty()) {
							if(!TestService.isInteger(requestMap.get("name")) && !TestService.isInteger(requestMap.get("prenom")) && !TestService.isInteger(requestMap.get("adresse")) && !TestService.isInteger(requestMap.get("role")) && !TestService.isInteger(requestMap.get("modeleVoiture")) && !TestService.isInteger(requestMap.get("email")) ) {
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
							}else {
								return EtronPrjUtils.getResponseEntity("Type is Not String , Please give a String Value", HttpStatus.BAD_REQUEST);
							}
						}else {
							return EtronPrjUtils.getResponseEntity("Argument Value Missing", HttpStatus.BAD_REQUEST);
						}
						
					}else {
						return EtronPrjUtils.getResponseEntity("Id Field is Empty", HttpStatus.BAD_REQUEST);
					}
					
				}else {
					return EtronPrjUtils.getResponseEntity("Negative Numbers Not Supported , Please Give a Positive Number", HttpStatus.BAD_REQUEST);
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
				if(TestService.intPositifNegatif(idUser)) {
					User user = userrepos.findById(idUser);
					userrepos.delete(user);
					return EtronPrjUtils.getResponseEntity("User Deleted Successfully", HttpStatus.OK);
				}else {
					return EtronPrjUtils.getResponseEntity("Negative or Zero idUser Not Supported , Please Give a Positive idUser", HttpStatus.BAD_REQUEST);
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
