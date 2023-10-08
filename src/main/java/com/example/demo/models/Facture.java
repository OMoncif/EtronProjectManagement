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
	private int idfacture;
	private LocalDate dateFacturation;
    private double montantTotal;
    
    @OneToOne
    private Contrat contrat;

	public int getIdfacture() {
		return idfacture;
	}

	public void setIdfacture(int idfacture) {
		this.idfacture = idfacture;
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
    
    
}
