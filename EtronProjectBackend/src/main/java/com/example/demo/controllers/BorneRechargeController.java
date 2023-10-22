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
import com.example.demo.services.BorneRechargeService;
import com.example.demo.utils.EtronPrjUtils;
import com.example.demoDTO.BorneRechargeDTO;

@RestController
@RequestMapping("/borne")
public class BorneRechargeController {

	@Autowired
	BorneRechargeService borneservice;
	
	@PostMapping(value="/add")
	public ResponseEntity<String> addBorneRecharge(@RequestBody Map<String,String> requestMap){
		try {
            return borneservice.addBorneRecharge(requestMap);
        }catch (Exception ex) {
            ex.printStackTrace();
        }
        return EtronPrjUtils.getResponseEntity(EtronPrjConstants.SOMETHING_WENT_WRONG , HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@PutMapping("/update")
    public ResponseEntity<String> modifierPlan( @RequestBody Map<String, String> requestMap) {
		try {
			return borneservice.update(requestMap);
		}catch (Exception ex) {
            ex.printStackTrace();
        }
        return EtronPrjUtils.getResponseEntity(EtronPrjConstants.SOMETHING_WENT_WRONG , HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @DeleteMapping("/delete/{idBorne}")
    public ResponseEntity<String> supprimerPlan(@PathVariable("idBorne") int idBorne) {
        try{
        	return borneservice.deleteBorne(idBorne);
        }catch (Exception ex) {
            ex.printStackTrace();
        }
        return EtronPrjUtils.getResponseEntity(EtronPrjConstants.SOMETHING_WENT_WRONG , HttpStatus.INTERNAL_SERVER_ERROR);
    }
	
    @GetMapping
	public ResponseEntity<List<BorneRechargeDTO>> getBornes(){
		try {
			return borneservice.getBorneRecharges();
			
		} catch(Exception e){
			e.printStackTrace();
		}
		return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
