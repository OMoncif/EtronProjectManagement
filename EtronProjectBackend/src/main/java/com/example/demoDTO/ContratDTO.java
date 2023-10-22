package com.example.demoDTO;


import java.time.LocalDate;

public class ContratDTO {
    private LocalDate dateDebut;
    private LocalDate dateFin;
    private double fraisMois;
    private String userEmail; // Email of the user
    private String abonnementPlanType; // Type of the AbonnementPlan

    // Getters and setters for the fields

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

    public double getFraisMois() {
        return fraisMois;
    }

    public void setFraisMois(double fraisMois) {
        this.fraisMois = fraisMois;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getAbonnementPlanType() {
        return abonnementPlanType;
    }

    public void setAbonnementPlanType(String abonnementPlanType) {
        this.abonnementPlanType = abonnementPlanType;
    }
}
