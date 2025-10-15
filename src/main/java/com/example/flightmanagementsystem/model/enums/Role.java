package com.example.flightmanagementsystem.model.enums;

public enum Role {
    USER("Utilisateur"),
    ADMIN("Administrateur"),
    MANAGER("Manager"),
    AGENT("Agent");
    
    private final String description;
    
    Role(String description) {
        this.description = description;
    }
    
    public String getDescription() {
        return description;
    }
}
