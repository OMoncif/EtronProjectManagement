package com.example.demo.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.constants.EtronPrjConstants;
import com.example.demo.models.Voiture;
import com.example.demo.services.VoitureService;
import com.example.demo.utils.EtronPrjUtils;

@RestController
@RequestMapping("/voiture")
public class VoitureController {

	@Autowired 
	VoitureService voitureservice;
	
	@PostMapping(value="/add")
	public ResponseEntity<String> addVoiture(@RequestBody Map<String, String> requestMap){
		try {
            return voitureservice.addVoiture(requestMap);
        }catch (Exception ex) {
            ex.printStackTrace();
        }
        return EtronPrjUtils.getResponseEntity(EtronPrjConstants.SOMETHING_WENT_WRONG , HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@GetMapping
	public ResponseEntity<List<Voiture>> getAbonnements(){
		try {
			return voitureservice.getVoitures();
			
		} catch(Exception e){
			e.printStackTrace();
		}
		return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@PutMapping("/update")
    public ResponseEntity<String> modifierPlan( @RequestBody Map<String, String> requestMap) {
		try {
			return voitureservice.update(requestMap);
		}catch (Exception ex) {
            ex.printStackTrace();
        }
        return EtronPrjUtils.getResponseEntity(EtronPrjConstants.SOMETHING_WENT_WRONG , HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PostMapping("/delete/{idVoiture}")
    public ResponseEntity<String> supprimerPlan(@PathVariable("idVoiture") int idVoiture) {
        try{
        	return voitureservice.deleteCar(idVoiture);
        }catch (Exception ex) {
            ex.printStackTrace();
        }
        return EtronPrjUtils.getResponseEntity(EtronPrjConstants.SOMETHING_WENT_WRONG , HttpStatus.INTERNAL_SERVER_ERROR);
    }
	
}
