package com.example.demo.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.services.ContratService;
import com.example.demoDTO.ContratDTO;

@RestController
@RequestMapping("/contrat")
public class ContratController {
	
	
	@Autowired
	private ContratService contratservice;
	
	@GetMapping
	public ResponseEntity<List<ContratDTO>> getContrats(){
		try {
			return contratservice.getContrats();
			
		} catch(Exception e){
			e.printStackTrace();
		}
		return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
