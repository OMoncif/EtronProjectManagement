package com.example.demo.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.demo.models.Paiement;
import com.example.demo.repositories.PaiementRepository;

@Service
public class PaiementService {
	
	@Autowired
	private PaiementRepository paiementrepos;
	
	public ResponseEntity<List<Paiement>> getPaiements(){
		try {
			List<Paiement> paiements = paiementrepos.findAll();
			return new ResponseEntity<>(paiements, HttpStatus.OK);
		} catch(Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
