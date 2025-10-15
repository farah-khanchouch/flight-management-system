package com.example.flightmanagementsystem.model.enums;

public enum StatutAeroport {
    OPERATIONNEL("Opérationnel"),
    FERME("Fermé"),
    MAINTENANCE("En maintenance"),
    CAPACITE_LIMITEE("Capacité limitée");
    
    private final String description;
    
    StatutAeroport(String description) {
        this.description = description;
    }
    
    public String getDescription() {
        return description;
    }
}
