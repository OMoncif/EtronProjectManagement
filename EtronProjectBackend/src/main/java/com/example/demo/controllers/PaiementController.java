package com.example.demo.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.services.PaiementService;
import com.example.demoDTO.PaiementDTO;

@RestController
@RequestMapping("/paiement")
public class PaiementController {
	
	@Autowired
	private PaiementService paiementservice;
	
	@GetMapping
	public ResponseEntity<List<PaiementDTO>> getPaiements(){
		try {
			return paiementservice.getPaiements();
			
		} catch(Exception e){
			e.printStackTrace();
		}
		return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
