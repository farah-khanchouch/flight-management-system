package com.example.flightmanagementsystem.model.enums;

public enum StatutEquipage {
    ACTIF("Actif"),
    EN_CONGE("En congé"),
    INDISPONIBLE("Indisponible"),
    EN_FORMATION("En formation"),
    RETIRE("Retraité");
    
    private final String description;
    
    StatutEquipage(String description) {
        this.description = description;
    }
    
    public String getDescription() {
        return description;
    }
}
