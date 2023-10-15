package com.example.demo.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.demo.models.Contrat;
import com.example.demo.repositories.ContratRepository;

@Service
public class ContratService {

	@Autowired
	private ContratRepository contratrepos;
	
	public ResponseEntity<List<Contrat>> getContrats(){
		try {
			List<Contrat> contrats = contratrepos.findAll();
			return new ResponseEntity<>(contrats, HttpStatus.OK);
		} catch(Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
