package com.example.demo.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.demo.JWT.JwtFilter;
import com.example.demo.models.Contrat;
import com.example.demo.repositories.ContratRepository;
import com.example.demoDTO.ContratDTO;

@Service
public class ContratService {

	@Autowired
	ContratRepository contratrepos;
	
	@Autowired
	JwtFilter jwtFilter;
	
	public ResponseEntity<List<ContratDTO>> getContrats() {
	    try {
	        if(jwtFilter.isAdmin()) {
	        	List<Contrat> contrats = contratrepos.findAll();
		        List<ContratDTO> contratDTOs = new ArrayList<>();

		        for (Contrat contrat : contrats) {
		            ContratDTO contratDTO = new ContratDTO();

		            // Map data from Contrat to ContratDTO
		            contratDTO.setDateDebut(contrat.getDateDebut());
		            contratDTO.setDateFin(contrat.getDateFin());
		            contratDTO.setFraisMois(contrat.getFraisMois());

		            // Access the related User and AbonnementPlan to get email and type
		            contratDTO.setUserEmail(contrat.getUser().getEmail());
		            contratDTO.setAbonnementPlanType(contrat.getPlan().getType());

		            contratDTOs.add(contratDTO);
		        }

		        return new ResponseEntity<>(contratDTOs, HttpStatus.OK);
	        }else {
	        	List<Contrat> contrats = contratrepos.getContratsByUsername(jwtFilter.getCurrentuser());
		        List<ContratDTO> contratDTOs = new ArrayList<>();

		        for (Contrat contrat : contrats) {
		            ContratDTO contratDTO = new ContratDTO();

		            // Map data from Contrat to ContratDTO
		            contratDTO.setDateDebut(contrat.getDateDebut());
		            contratDTO.setDateFin(contrat.getDateFin());
		            contratDTO.setFraisMois(contrat.getFraisMois());

		            // Access the related User and AbonnementPlan to get email and type
		            contratDTO.setUserEmail(contrat.getUser().getEmail());
		            contratDTO.setAbonnementPlanType(contrat.getPlan().getType());

		            contratDTOs.add(contratDTO);
		        }

		        return new ResponseEntity<>(contratDTOs, HttpStatus.OK);
	        }
	    	
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	/*public ResponseEntity<List<Contrat>> getContrats(){
		try {
			if(jwtFilter.isAdmin()) {
				List<Contrat> contrats = contratrepos.findAll();
				return new ResponseEntity<>(contrats, HttpStatus.OK);
			}else {
				return new ResponseEntity<>(new ArrayList<>(), HttpStatus.UNAUTHORIZED);
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
	}*/
}
