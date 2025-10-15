package com.example.flightmanagementsystem.model.enums;

public enum Genre {
    HOMME("Homme"),
    FEMME("Femme"),
    AUTRE("Autre");
    
    private final String description;
    
    Genre(String description) {
        this.description = description;
    }
    
    public String getDescription() {
        return description;
    }
}
