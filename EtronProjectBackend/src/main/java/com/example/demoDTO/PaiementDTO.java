package com.example.demoDTO;


import java.time.LocalDate;

public class PaiementDTO {
    private LocalDate datePaiement;
    private double montant;
    private String userEmail; // Include the email of the associated user

    // Getters and setters for the fields

    public LocalDate getDatePaiement() {
        return datePaiement;
    }

    public void setDatePaiement(LocalDate datePaiement) {
        this.datePaiement = datePaiement;
    }

    public double getMontant() {
        return montant;
    }

    public void setMontant(double montant) {
        this.montant = montant;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }
}
