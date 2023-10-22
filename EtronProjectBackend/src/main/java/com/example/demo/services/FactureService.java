package com.example.demo.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.demo.JWT.JwtFilter;
import com.example.demo.models.Facture;
import com.example.demo.repositories.FactureRepository;
import com.example.demoDTO.FactureDTO;


@Service
public class FactureService {

	@Autowired
	private FactureRepository facturerepos;
	
	@Autowired
	JwtFilter jwtFilter;
	
	public ResponseEntity<List<FactureDTO>> getFactures() {
	    try {
	    	if(jwtFilter.isAdmin()) {
	    		List<Facture> factures = facturerepos.findAll();
		        List<FactureDTO> factureDTOs = new ArrayList<>();

		        for (Facture facture : factures) {
		            FactureDTO factureDTO = new FactureDTO();

		            // Map data from Facture to FactureDTO
		            factureDTO.setDateFacturation(facture.getDateFacturation());
		            factureDTO.setMontantTotal(facture.getMontantTotal());
		            factureDTO.setStatus(facture.getStatus());

		            // Retrieve the associated user's email
		            String userEmail = facture.getContrat().getUser().getEmail();
		            factureDTO.setUserEmail(userEmail);

		            factureDTOs.add(factureDTO);
		        }

		        return new ResponseEntity<>(factureDTOs, HttpStatus.OK);
	    	}else {
	    		List<Facture> factures = facturerepos.getFacturesByUsername(jwtFilter.getCurrentuser());
		        List<FactureDTO> factureDTOs = new ArrayList<>();

		        for (Facture facture : factures) {
		            FactureDTO factureDTO = new FactureDTO();

		            // Map data from Facture to FactureDTO
		            factureDTO.setDateFacturation(facture.getDateFacturation());
		            factureDTO.setMontantTotal(facture.getMontantTotal());
		            factureDTO.setStatus(facture.getStatus());

		            // Retrieve the associated user's email
		            String userEmail = facture.getContrat().getUser().getEmail();
		            factureDTO.setUserEmail(userEmail);

		            factureDTOs.add(factureDTO);
		        }

		        return new ResponseEntity<>(factureDTOs, HttpStatus.OK);
	    	}
	        
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
