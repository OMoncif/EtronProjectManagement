package com.example.demo.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.constants.EtronPrjConstants;
import com.example.demo.services.AbonnementPlanService;
import com.example.demo.utils.EtronPrjUtils;
import com.example.demoDTO.AbonnementPlanDTO;

@RestController
@RequestMapping("plan")
public class AbonnementPlanController {
	
	@Autowired
	private AbonnementPlanService planservice;
	
	@PostMapping(value="/add")
	public ResponseEntity<String> AjoutAbonnement(@RequestBody Map<String,String> requestMap){
		try {
            return planservice.addAbonnement(requestMap);
        }catch (Exception ex) {
            ex.printStackTrace();
        }
        return EtronPrjUtils.getResponseEntity(EtronPrjConstants.SOMETHING_WENT_WRONG , HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@PostMapping(value="/annuler")
	public ResponseEntity<String> annulerAbonnement(@RequestBody Map<String,String> requestMap){
		try {
            return planservice.annulerAbonnement(requestMap);
        }catch (Exception ex) {
            ex.printStackTrace();
        }
        return EtronPrjUtils.getResponseEntity(EtronPrjConstants.SOMETHING_WENT_WRONG , HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@GetMapping
	public ResponseEntity<List<AbonnementPlanDTO>> getAbonnements(){
		try {
			return planservice.getAbonnementPlans();
			
		} catch(Exception e){
			e.printStackTrace();
		}
		return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@PostMapping("/update")
    public ResponseEntity<String> modifierPlan(@RequestBody Map<String, String> requestMap) {
		try {
			return planservice.update(requestMap);
		}catch (Exception ex) {
            ex.printStackTrace();
        }
        return EtronPrjUtils.getResponseEntity(EtronPrjConstants.SOMETHING_WENT_WRONG , HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PostMapping("/delete/{type}")
    public ResponseEntity<String> supprimerPlan(@PathVariable("type") String type) {
        try{
        	return planservice.deletePlan(type);
        }catch (Exception ex) {
            ex.printStackTrace();
        }
        return EtronPrjUtils.getResponseEntity(EtronPrjConstants.SOMETHING_WENT_WRONG , HttpStatus.INTERNAL_SERVER_ERROR);
    }
	
}