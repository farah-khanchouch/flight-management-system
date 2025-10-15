package com.example.flightmanagementsystem.model.enums;

public enum StatutAvion {
    OPERATIONNEL("Opérationnel"),
    EN_MAINTENANCE("En maintenance"),
    HORS_SERVICE("Hors service"),
    EN_VOL("En vol"),
    RESERVE("Réservé");
    
    private final String description;
    
    StatutAvion(String description) {
        this.description = description;
    }
    
    public String getDescription() {
        return description;
    }
}
