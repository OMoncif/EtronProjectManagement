package com.example.demo.models;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class Voiture {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private String modele;
	
	private LocalDate dateAjoutVoiture;
	
	@OneToOne
	private User user;

	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getModele() {
		return modele;
	}

	public void setModele(String modele) {
		this.modele = modele;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public LocalDate getDateAjoutVoiture() {
		return dateAjoutVoiture;
	}

	public void setDateAjoutVoiture(LocalDate dateAjoutVoiture) {
		this.dateAjoutVoiture = dateAjoutVoiture;
	}
	
	
	
}
