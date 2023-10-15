package com.example.demo.models;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Recharge {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double quantiteEnergie; // La quantité d'énergie en kWh

    private String typeCharge; // Type de charge (AC, DC/HPC, haute puissance)

    private LocalDateTime dateHeureRecharge; // Date et heure de la recharge
    
    private int DureeRecharge;
    
    @ManyToOne
    private User user;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public double getQuantiteEnergie() {
		return quantiteEnergie;
	}

	public void setQuantiteEnergie(double quantiteEnergie) {
		this.quantiteEnergie = quantiteEnergie;
	}

	public String getTypeCharge() {
		return typeCharge;
	}

	public void setTypeCharge(String typeCharge) {
		this.typeCharge = typeCharge;
	}

	public LocalDateTime getDateHeureRecharge() {
		return dateHeureRecharge;
	}

	public void setDateHeureRecharge(LocalDateTime dateHeureRecharge) {
		this.dateHeureRecharge = dateHeureRecharge;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public int getDureeRecharge() {
		return DureeRecharge;
	}

	public void setDureeRecharge(int dureeRecharge) {
		DureeRecharge = dureeRecharge;
	}
	
	

    
}
