package com.example.demo.services;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.demo.JWT.JwtFilter;
import com.example.demo.repositories.AbonnementPlanRepository;
import com.example.demo.repositories.BorneRechargeRepository;
import com.example.demo.repositories.FactureRepository;
import com.example.demo.repositories.PaiementRepository;

@Service
public class DashboardService {
	
	@Autowired
	AbonnementPlanRepository planrepos;
	
	@Autowired
	PaiementRepository paiementrepos;
	
	@Autowired
	BorneRechargeRepository bornerepos;
	
	@Autowired
	FactureRepository facturerepos;
	
	@Autowired
	JwtFilter jwtFilter;
	
	
	public ResponseEntity<Map<String, Object>> getCount() {
		try {
			Map<String, Object> map = new HashMap<>();
			if(jwtFilter.isAdmin()) {
				map.put("Abonnement", planrepos.count());
				map.put("BorneRecharge", bornerepos.count());
				map.put("Paiement", paiementrepos.count());
				map.put("Facture", facturerepos.count());
				return new ResponseEntity<>(map, HttpStatus.OK);
			}
			else {
				map.put("Abonnement", planrepos.count());
				map.put("BorneRecharge", bornerepos.count());
				map.put("Paiement", paiementrepos.getPaiementCountByUserName(jwtFilter.getCurrentuser()));
				map.put("Facture", facturerepos.getFactureCountByUserName(jwtFilter.getCurrentuser()));
				return new ResponseEntity<>(map, HttpStatus.OK);
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
