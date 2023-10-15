package com.example.demo.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.constants.EtronPrjConstants;
import com.example.demo.models.User;
import com.example.demo.services.UserService;
import com.example.demo.utils.EtronPrjUtils;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	UserService userservice;
	
	@GetMapping
	public ResponseEntity<List<User>> getUsers(){
		try {
			return userservice.getUsers();
			
		} catch(Exception e){
			e.printStackTrace();
		}
		return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@PostMapping("/update")
    public ResponseEntity<String> modifierPlan( @RequestBody Map<String, String> requestMap) {
		try {
			return userservice.update(requestMap);
		}catch (Exception ex) {
            ex.printStackTrace();
        }
        return EtronPrjUtils.getResponseEntity(EtronPrjConstants.SOMETHING_WENT_WRONG , HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PostMapping("/delete/{idUser}")
    public ResponseEntity<String> supprimerPlan(@PathVariable("idUser") int idUser) {
        try{
        	return userservice.deleteUser(idUser);
        }catch (Exception ex) {
            ex.printStackTrace();
        }
        return EtronPrjUtils.getResponseEntity(EtronPrjConstants.SOMETHING_WENT_WRONG , HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
