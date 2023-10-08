package com.example.demo.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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
	public ResponseEntity<String> inscriptionUser(Map<String, String> requestMap){
		try {
            return etronabonnementService.inscrireUtilisateur(requestMap);
        }catch (Exception ex) {
            ex.printStackTrace();
        }
        return EtronPrjUtils.getResponseEntity(EtronPrjConstants.SOMETHING_WENT_WRONG , HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@PostMapping(value="login")
	public ResponseEntity<String> login(Map<String, String> requestMap) {
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
	public ResponseEntity<String> changePassword(Map<String, String> requestMap) {
		try {
			return etronabonnementService.changePassword(requestMap);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return EtronPrjUtils.getResponseEntity(EtronPrjConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	@PostMapping(value="/forgotPassword")
	public ResponseEntity<String> forgotPassword(Map<String, String> requestMap) {
		try {
			return etronabonnementService.forgotPassword(requestMap);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return EtronPrjUtils.getResponseEntity(EtronPrjConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	
	@PostMapping(value="/choisirplan")
	public ResponseEntity<String> choisirAbonnement(@RequestParam("idUser") int idUser ,@RequestParam("typeAbonnement") String typeAbonnement ){
		try {
            return etronabonnementService.choisirFormuleAbonnement(idUser, typeAbonnement);
        }catch (Exception ex) {
            ex.printStackTrace();
        }
        return EtronPrjUtils.getResponseEntity(EtronPrjConstants.SOMETHING_WENT_WRONG , HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@PostMapping(value="/paiement")
	public ResponseEntity<String> effectuerPaiement(@RequestParam("idUser") int idUser ,@RequestParam("typeAbonnement") String typeAbonnement ){
		try {
            return etronabonnementService.effectuerPaiementAvecCode(idUser, typeAbonnement);
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
	
	@GetMapping(value="calculerFactureMensuelle")
	public ResponseEntity<Facture> calculerFactureMensuelle(@RequestParam("idUser") int idUser,@RequestParam("mois") int mois, @RequestParam("annee") int annee){
		try {
			return etronabonnementService.calculerFactureMensuelle(idUser, mois, annee);
			
		} catch(Exception e){
			e.printStackTrace();
		}
		return new ResponseEntity<>(new Facture(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
