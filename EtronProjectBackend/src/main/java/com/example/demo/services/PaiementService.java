package com.example.demo.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.demo.JWT.JwtFilter;
import com.example.demo.models.Paiement;
import com.example.demo.repositories.PaiementRepository;
import com.example.demoDTO.PaiementDTO;

@Service
public class PaiementService {
	
	@Autowired
	private PaiementRepository paiementrepos;
	
	@Autowired
	JwtFilter jwtFilter;
	
	public ResponseEntity<List<PaiementDTO>> getPaiements() {
	    try {
	    	if(jwtFilter.isAdmin()) {
	    		List<Paiement> paiements = paiementrepos.findAll();
		        List<PaiementDTO> paiementDTOs = new ArrayList<>();

		        for (Paiement paiement : paiements) {
		            PaiementDTO paiementDTO = new PaiementDTO();

		            // Map data from Paiement to PaiementDTO
		            paiementDTO.setDatePaiement(paiement.getDatePaiement());
		            paiementDTO.setMontant(paiement.getMontant());
		            paiementDTO.setUserEmail(paiement.getUser().getEmail()); // Get the user's email

		            paiementDTOs.add(paiementDTO);
		        }

		        return new ResponseEntity<>(paiementDTOs, HttpStatus.OK);
	    	}else {
	    		List<Paiement> paiements = paiementrepos.getPaiementsByUsername(jwtFilter.getCurrentuser());
		        List<PaiementDTO> paiementDTOs = new ArrayList<>();

		        for (Paiement paiement : paiements) {
		            PaiementDTO paiementDTO = new PaiementDTO();

		            // Map data from Paiement to PaiementDTO
		            paiementDTO.setDatePaiement(paiement.getDatePaiement());
		            paiementDTO.setMontant(paiement.getMontant());
		            paiementDTO.setUserEmail(paiement.getUser().getEmail()); // Get the user's email

		            paiementDTOs.add(paiementDTO);
		        }

		        return new ResponseEntity<>(paiementDTOs, HttpStatus.OK);
	    	}
	        
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
