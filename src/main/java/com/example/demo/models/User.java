package com.example.demo.models;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

//import lombok.Data;


@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "user")
public class User implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;
	
	@Column(name = "name")
	private String name;
	
	@Column(name= "prenom")
	private String prenom;
	
	@Column(name= "adresse")
	private String adresse;
	
	private String contactnumber;

	@Column(name = "email")
	private String email;
	
	@Column(name="dateinscription")
    private LocalDate dateInscription;
	
	private String password;
	
	private String role;
	
	private String modeleVoiture;

/*	
	@Column(name = "password")
	private String password;

	@Column(name = "role")
	private String role;
*/
	
	 @OneToOne(mappedBy = "user")
	 private Contrat contrat;
	 
	 @OneToMany(mappedBy = "user")
	 private List<Paiement> paiements;
	 
	 @OneToOne(mappedBy = "user")
	 private Voiture voiture;
	 
	 @OneToMany(mappedBy = "user")
	 private List<EtatChargeBatterie> etatsChargeBatterie;
	 
	 @OneToMany(mappedBy="user")
	 private List<BorneRecharge> bornes;
	 
	 @OneToMany(mappedBy="user")
	 private List<Recharge> recharges;
	 
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public String getAdresse() {
		return adresse;
	}

	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}

	public Contrat getContrat() {
		return contrat;
	}

	public void setContrat(Contrat contrat) {
		this.contrat = contrat;
	}

	public List<Paiement> getPaiements() {
		return paiements;
	}

	public void setPaiements(List<Paiement> paiements) {
		this.paiements = paiements;
	}

	public LocalDate getDateInscription() {
		return dateInscription;
	}

	public void setDateInscription(LocalDate dateInscription) {
		this.dateInscription = dateInscription;
	}

	public Voiture getVoiture() {
		return voiture;
	}

	public void setVoiture(Voiture voiture) {
		this.voiture = voiture;
	}

	public String getContactnumber() {
		return contactnumber;
	}

	public void setContactnumber(String contactnumber) {
		this.contactnumber = contactnumber;
	}

	public List<EtatChargeBatterie> getEtatsChargeBatterie() {
		return etatsChargeBatterie;
	}

	public void setEtatsChargeBatterie(List<EtatChargeBatterie> etatsChargeBatterie) {
		this.etatsChargeBatterie = etatsChargeBatterie;
	}

	public List<BorneRecharge> getBornes() {
		return bornes;
	}

	public void setBornes(List<BorneRecharge> bornes) {
		this.bornes = bornes;
	}

	public List<Recharge> getRecharges() {
		return recharges;
	}

	public void setRecharges(List<Recharge> recharges) {
		this.recharges = recharges;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getModeleVoiture() {
		return modeleVoiture;
	}

	public void setModeleVoiture(String modeleVoiture) {
		this.modeleVoiture = modeleVoiture;
	}
	
	
	
	
	
	
	
	
}