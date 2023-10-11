package com.example.demo.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.constants.EtronPrjConstants;
import com.example.demo.models.BorneRecharge;
import com.example.demo.models.Facture;
import com.example.demo.services.EtronAbonnementService;
import com.example.demo.utils.EtronPrjUtils;




@RestController
public class EtronAbonnementController {
	
	@Autowired
	private EtronAbonnementService etronabonnementService;
	
	@PostMapping(value = "/inscription")
	public ResponseEntity<String> inscriptionUser(@RequestBody Map<String, String> requestMap){
		try {
            return etronabonnementService.inscrireUtilisateur(requestMap);
        }catch (Exception ex) {
            ex.printStackTrace();
        }
        return EtronPrjUtils.getResponseEntity(EtronPrjConstants.SOMETHING_WENT_WRONG , HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@PostMapping(value="login")
	public ResponseEntity<String> login(@RequestBody Map<String, String> requestMap) {
		try {
			return etronabonnementService.login(requestMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return EtronPrjUtils.getResponseEntity(EtronPrjConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@GetMapping(value="/checkToken")
	public ResponseEntity<String> checkToken() {
		try {
			return etronabonnementService.checkToken();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return EtronPrjUtils.getResponseEntity(EtronPrjConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@PostMapping(value="/changePassword")
	public ResponseEntity<String> changePassword(@RequestBody Map<String, String> requestMap) {
		try {
			return etronabonnementService.changePassword(requestMap);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return EtronPrjUtils.getResponseEntity(EtronPrjConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	@PostMapping(value="/forgotPassword")
	public ResponseEntity<String> forgotPassword(@RequestBody Map<String, String> requestMap) {
		try {
			return etronabonnementService.forgotPassword(requestMap);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return EtronPrjUtils.getResponseEntity(EtronPrjConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	
	@PostMapping(value="/choisirplan")
	public ResponseEntity<String> choisirAbonnement(@RequestBody Map<String,String> requestMap ){
		try {
            return etronabonnementService.choisirFormuleAbonnement(requestMap);
        }catch (Exception ex) {
            ex.printStackTrace();
        }
        return EtronPrjUtils.getResponseEntity(EtronPrjConstants.SOMETHING_WENT_WRONG , HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@PostMapping(value="/paiement")
	public ResponseEntity<String> effectuerPaiement(@RequestBody Map<String,String> requestMap ){
		try {
            return etronabonnementService.effectuerPaiementAvecCode(requestMap);
        }catch (Exception ex) {
            ex.printStackTrace();
        }
        return EtronPrjUtils.getResponseEntity(EtronPrjConstants.SOMETHING_WENT_WRONG , HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@GetMapping(value="/getBornes")
	public ResponseEntity<List<BorneRecharge>> ListerBornesRechargeUser(@RequestParam("userLatitude") double userLatitude ,@RequestParam("userLongitude") double userLongitude ,@RequestParam("rayonDistanceMax") double rayonDistanceMax){
		try {
			return etronabonnementService.ListerBornesRechargeUser(userLatitude, userLongitude, rayonDistanceMax);
			
		} catch(Exception e){
			e.printStackTrace();
		}
		return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@PostMapping(value="/calculerFactureMensuelle")
	public ResponseEntity<String> calculerFactureMensuelle(@RequestBody Map<String,String> requestMap){
		try {
			return etronabonnementService.calculerFactureMensuelle(requestMap);
			
		} catch(Exception e){
			e.printStackTrace();
		}
		return EtronPrjUtils.getResponseEntity(EtronPrjConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@PostMapping(value="/AjoutVoiture")
	public ResponseEntity<String> addVoiture(@RequestBody Map<String, String> requestMap){
		try {
            return etronabonnementService.addVoiture(requestMap);
        }catch (Exception ex) {
            ex.printStackTrace();
        }
        return EtronPrjUtils.getResponseEntity(EtronPrjConstants.SOMETHING_WENT_WRONG , HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	
	
	@PostMapping(value="/AjoutRecharge")
	public ResponseEntity<String> AjoutRecharge(@RequestBody Map<String, String> requestMap){
		try {
            return etronabonnementService.addRecharge(requestMap);
        }catch (Exception ex) {
            ex.printStackTrace();
        }
        return EtronPrjUtils.getResponseEntity(EtronPrjConstants.SOMETHING_WENT_WRONG , HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@PostMapping(value="/AjoutAbonnement")
	public ResponseEntity<String> AjoutAbonnement(@RequestBody Map<String,String> requestMap){
		try {
            return etronabonnementService.addAbonnement(requestMap);
        }catch (Exception ex) {
            ex.printStackTrace();
        }
        return EtronPrjUtils.getResponseEntity(EtronPrjConstants.SOMETHING_WENT_WRONG , HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	//addBorneRecharge
	@PostMapping(value="/addBorneRecharge")
	public ResponseEntity<String> addBorneRecharge(@RequestBody Map<String,String> requestMap){
		try {
            return etronabonnementService.addBorneRecharge(requestMap);
        }catch (Exception ex) {
            ex.printStackTrace();
        }
        return EtronPrjUtils.getResponseEntity(EtronPrjConstants.SOMETHING_WENT_WRONG , HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
}
