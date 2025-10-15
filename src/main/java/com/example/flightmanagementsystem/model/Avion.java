package com.example.flightmanagementsystem.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import com.example.flightmanagementsystem.model.enums.StatutAvion;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "avions")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Avion {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "numero_serie", unique = true, nullable = false, length = 50)
    private String numeroSerie;
    
    @Column(name = "type_avion", nullable = false, length = 50)
    private String typeAvion; // Boeing 737, Airbus A320, etc.
    
    @Column(name = "modele", nullable = false, length = 50)
    private String modele;
    
    @Column(name = "capacite", nullable = false)
    private Integer capacite;
    
    @Column(name = "annee_fabrication", nullable = false)
    private Integer anneeFabrication;
    
    @Column(name = "capacite_carburant")
    private Double capaciteCarburant; // en litres
    
    @Column(name = "autonomie")
    private Integer autonomie; // en km
    
    @Enumerated(EnumType.STRING)
    @Column(name = "statut", nullable = false)
    private StatutAvion statut = StatutAvion.OPERATIONNEL;
    
    @Column(name = "derniere_maintenance")
    private LocalDateTime derniereMantenace;
    
    @Column(name = "prochaine_maintenance")
    private LocalDateTime prochaineMaintenace;
    
    @OneToMany(mappedBy = "avion", cascade = CascadeType.ALL)
    @ToString.Exclude
    private Set<Vol> vols = new HashSet<>();
    
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }
    
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
    
    // Helper methods
    public void addVol(Vol vol) {
        this.vols.add(vol);
        vol.setAvion(this);
    }
    
    public void removeVol(Vol vol) {
        this.vols.remove(vol);
        vol.setAvion(null);
    }
    
    public boolean isDisponible() {
        return this.statut == StatutAvion.OPERATIONNEL;
    }
}
