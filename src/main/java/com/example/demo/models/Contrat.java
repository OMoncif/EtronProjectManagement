package com.example.demo.models;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "contrat")
public class Contrat {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idcontrat;
	
	private LocalDate dateDebut;
    private LocalDate dateFin;
    private double FraisMois;
    
    @ManyToOne
    private AbonnementPlan plan;
    
    @OneToOne
    private User user;
    
    @OneToOne(mappedBy= "contrat")
    private Facture facture;

	public Facture getFacture() {
		return facture;
	}

	public void setFacture(Facture facture) {
		this.facture = facture;
	}

	public int getIdcontrat() {
		return idcontrat;
	}

	public void setIdcontrat(int idcontrat) {
		this.idcontrat = idcontrat;
	}

	public LocalDate getDateDebut() {
		return dateDebut;
	}

	public void setDateDebut(LocalDate dateDebut) {
		this.dateDebut = dateDebut;
	}

	public LocalDate getDateFin() {
		return dateFin;
	}

	public void setDateFin(LocalDate dateFin) {
		this.dateFin = dateFin;
	}

	public AbonnementPlan getPlan() {
		return plan;
	}

	public void setPlan(AbonnementPlan plan) {
		this.plan = plan;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public double getFraisMois() {
		return FraisMois;
	}

	public void setFraisMois(double fraisMois) {
		FraisMois = fraisMois;
	}
	
    
    
}
