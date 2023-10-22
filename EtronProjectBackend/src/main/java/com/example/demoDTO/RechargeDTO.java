package com.example.demoDTO;


import java.time.LocalDateTime;

public class RechargeDTO {
    private double quantiteEnergie;
    private String typeCharge;
    private LocalDateTime dateHeureRecharge;
    private int DureeRecharge;
    private String userEmail;

    // Getters and setters for the fields

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

    public int getDureeRecharge() {
        return DureeRecharge;
    }

    public void setDureeRecharge(int dureeRecharge) {
        this.DureeRecharge = dureeRecharge;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }
}
