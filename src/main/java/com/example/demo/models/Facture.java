package com.example.demo.models;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "facture")
public class Facture {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private LocalDate dateFacturation;
    private double montantTotal;
    private String status;
    
    @OneToOne
    private Contrat contrat;

	public int getIdfacture() {
		return id;
	}

	public void setIdfacture(int id) {
		this.id = id;
	}

	public LocalDate getDateFacturation() {
		return dateFacturation;
	}

	public void setDateFacturation(LocalDate dateFacturation) {
		this.dateFacturation = dateFacturation;
	}

	public double getMontantTotal() {
		return montantTotal;
	}

	public void setMontantTotal(double montantTotal) {
		this.montantTotal = montantTotal;
	}

	public Contrat getContrat() {
		return contrat;
	}

	public void setContrat(Contrat contrat) {
		this.contrat = contrat;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
    
    
}
