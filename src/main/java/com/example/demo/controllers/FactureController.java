package com.example.demo.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.models.Facture;
import com.example.demo.services.FactureService;

@RestController
@RequestMapping("/facture")
public class FactureController {
	
	@Autowired
	private FactureService factureservice;
	
	@GetMapping
	public ResponseEntity<List<Facture>> getFactures(){
		try {
			return factureservice.getFactures();
			
		} catch(Exception e){
			e.printStackTrace();
		}
		return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
