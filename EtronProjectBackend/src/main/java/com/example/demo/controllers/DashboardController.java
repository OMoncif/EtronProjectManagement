package com.example.demo.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.services.DashboardService;


@RestController
@RequestMapping(path = "/dashboard")
public class DashboardController {
	
	@Autowired
	DashboardService dashboardService;
	
	@GetMapping(value="/details")
	public ResponseEntity<Map<String, Object>> getCount() {
		try {
			return dashboardService.getCount();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
