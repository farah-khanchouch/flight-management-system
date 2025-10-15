package com.example.flightmanagementsystem.model.enums;

public enum FonctionEquipage {
    PILOTE("Pilote"),
    COPILOTE("Copilote"),
    CHEF_CABINE("Chef de cabine"),
    HOTESSE("Hôtesse de l'air"),
    STEWARD("Steward"),
    MECANICIEN_VOL("Mécanicien de vol"),
    AGENT_SECURITE("Agent de sécurité");
    
    private final String description;
    
    FonctionEquipage(String description) {
        this.description = description;
    }
    
    public String getDescription() {
        return description;
    }
}
