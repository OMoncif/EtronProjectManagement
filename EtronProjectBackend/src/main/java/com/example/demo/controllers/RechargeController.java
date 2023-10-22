package com.example.demo.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.constants.EtronPrjConstants;
import com.example.demo.services.RechargeService;
import com.example.demo.utils.EtronPrjUtils;
import com.example.demoDTO.RechargeDTO;

@RestController
@RequestMapping("/recharge")
public class RechargeController {
	
	@Autowired
	RechargeService rechargeservice;
	
	@PostMapping(value="/add")
	public ResponseEntity<String> AjoutRecharge(@RequestBody Map<String, String> requestMap){
		try {
            return rechargeservice.addRecharge(requestMap);
        }catch (Exception ex) {
            ex.printStackTrace();
        }
        return EtronPrjUtils.getResponseEntity(EtronPrjConstants.SOMETHING_WENT_WRONG , HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@GetMapping
	public ResponseEntity<List<RechargeDTO>> getRecharges(){
		try {
			return rechargeservice.getRecharges();
			
		} catch(Exception e){
			e.printStackTrace();
		}
		return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
}
