package com.example.demo.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.demo.models.Facture;
import com.example.demo.repositories.FactureRepository;

@Service
public class FactureService {

	@Autowired
	private FactureRepository facturerepos;
	
	public ResponseEntity<List<Facture>> getFactures(){
		try {
			List<Facture> factures = facturerepos.findAll();
			return new ResponseEntity<>(factures, HttpStatus.OK);
		} catch(Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
