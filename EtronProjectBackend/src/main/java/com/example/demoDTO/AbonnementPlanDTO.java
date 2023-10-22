package com.example.demoDTO;


public class AbonnementPlanDTO {
    private int id;
    private String type;
    private double fraismois;
    private int dureeContrat;
    private double fraisAC;
    private double fraisDCHPC;
    private double fraisHautePuissance;
    private double fraisBlocageAC;
    private double fraisBlocageDC;

    // Getters and setters for the fields

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getFraismois() {
        return fraismois;
    }

    public void setFraismois(double fraismois) {
        this.fraismois = fraismois;
    }

    public int getDureeContrat() {
        return dureeContrat;
    }

    public void setDureeContrat(int dureeContrat) {
        this.dureeContrat = dureeContrat;
    }

    public double getFraisAC() {
        return fraisAC;
    }

    public void setFraisAC(double fraisAC) {
        this.fraisAC = fraisAC;
    }

    public double getFraisDCHPC() {
        return fraisDCHPC;
    }

    public void setFraisDCHPC(double fraisDCHPC) {
        this.fraisDCHPC = fraisDCHPC;
    }

    public double getFraisHautePuissance() {
        return fraisHautePuissance;
    }

    public void setFraisHautePuissance(double fraisHautePuissance) {
        this.fraisHautePuissance = fraisHautePuissance;
    }

    public double getFraisBlocageAC() {
        return fraisBlocageAC;
    }

    public void setFraisBlocageAC(double fraisBlocageAC) {
        this.fraisBlocageAC = fraisBlocageAC;
    }

    public double getFraisBlocageDC() {
        return fraisBlocageDC;
    }

    public void setFraisBlocageDC(double fraisBlocageDC) {
        this.fraisBlocageDC = fraisBlocageDC;
    }
}
