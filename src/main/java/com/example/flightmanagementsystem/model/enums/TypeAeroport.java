package com.example.flightmanagementsystem.model.enums;

public enum TypeAeroport {
    INTERNATIONAL("International"),
    NATIONAL("National"),
    REGIONAL("Régional"),
    PRIVE("Privé");
    
    private final String description;
    
    TypeAeroport(String description) {
        this.description = description;
    }
    
    public String getDescription() {
        return description;
    }
}
