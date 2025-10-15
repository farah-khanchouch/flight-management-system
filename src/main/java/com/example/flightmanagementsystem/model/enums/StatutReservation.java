package com.example.flightmanagementsystem.model.enums;

public enum StatutReservation {
    EN_ATTENTE("En attente de paiement"),
    CONFIRMEE("Confirmée"),
    ANNULEE("Annulée"),
    REMBOURSEE("Remboursée"),
    EXPIREE("Expirée");
    
    private final String description;
    
    StatutReservation(String description) {
        this.description = description;
    }
    
    public String getDescription() {
        return description;
    }
}
