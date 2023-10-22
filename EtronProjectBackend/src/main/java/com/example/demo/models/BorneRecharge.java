package com.example.demo.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "bornerecharge")
public class BorneRecharge {
	
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private int id;
	private String typecharge;
	private double latitude;
	private double longitude;
    private boolean disponible;
    
    @ManyToOne
    private User user;
    
	public int getId() {
		return id;
	}
	public void setId(int idBorne) {
		this.id = idBorne;
	}
	public String getTypecharge() {
		return typecharge;
	}
	public void setTypecharge(String typecharge) {
		this.typecharge = typecharge;
	}
	public boolean isDisponible() {
		return disponible;
	}
	public void setDisponible(boolean disponible) {
		this.disponible = disponible;
	}
	public double getLatitude() {
		return latitude;
	}
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	public double getLongitude() {
		return longitude;
	}
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	
    
    
}
