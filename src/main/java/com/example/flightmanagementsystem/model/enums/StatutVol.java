package com.example.flightmanagementsystem.model.enums;

public enum StatutVol {
    PROGRAMME("Programmé"),
    EN_COURS("En cours"),
    A_L_HEURE("À l'heure"),
    RETARDE("Retardé"),
    ANNULE("Annulé"),
    ATTERRI("Atterri"),
    EMBARQUEMENT("Embarquement");
    
    private final String description;
    
    StatutVol(String description) {
        this.description = description;
    }
    
    public String getDescription() {
        return description;
    }
}
