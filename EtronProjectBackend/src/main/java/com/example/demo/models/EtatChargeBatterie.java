package com.example.demo.models;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class EtatChargeBatterie {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private double pourcentageCharge;
    private LocalDateTime dateHeureMesure;


    @ManyToOne
    private User user;


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public double getPourcentageCharge() {
		return pourcentageCharge;
	}


	public void setPourcentageCharge(double pourcentageCharge) {
		this.pourcentageCharge = pourcentageCharge;
	}


	public LocalDateTime getDateHeureMesure() {
		return dateHeureMesure;
	}


	public void setDateHeureMesure(LocalDateTime dateHeureMesure) {
		this.dateHeureMesure = dateHeureMesure;
	}


	public User getUser() {
		return user;
	}


	public void setUser(User user) {
		this.user = user;
	}
    
    
}
