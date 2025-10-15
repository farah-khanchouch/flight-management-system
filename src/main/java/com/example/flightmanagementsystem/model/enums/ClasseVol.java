package com.example.flightmanagementsystem.model.enums;

public enum ClasseVol {
    ECONOMIQUE("Classe économique", 1.0),
    ECONOMIQUE_PREMIUM("Économique premium", 1.3),
    AFFAIRES("Classe affaires", 2.0),
    PREMIERE("Première classe", 3.5);
    
    private final String description;
    private final double multiplicateur;
    
    ClasseVol(String description, double multiplicateur) {
        this.description = description;
        this.multiplicateur = multiplicateur;
    }
    
    public String getDescription() {
        return description;
    }
    
    public double getMultiplicateur() {
        return multiplicateur;
    }
}
