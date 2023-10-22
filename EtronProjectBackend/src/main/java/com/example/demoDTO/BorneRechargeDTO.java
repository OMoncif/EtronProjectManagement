package com.example.demoDTO;


public class BorneRechargeDTO {
	private int idBorne;
    private String typecharge;
    private double latitude;
    private double longitude;
    private boolean disponible;

    // Getters and setters for the fields

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

	public int getIdBorne() {
		return idBorne;
	}

	public void setIdBorne(int idBorne) {
		this.idBorne = idBorne;
	}
    
}
