package com.example.flightmanagementsystem.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import com.example.flightmanagementsystem.model.enums.TypeAeroport;
import com.example.flightmanagementsystem.model.enums.StatutAeroport;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "aeroports")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Aeroport {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "code_iata", unique = true, nullable = false, length = 3)
    private String codeIATA;
    
    @Column(name = "code_icao", unique = true, length = 4)
    private String codeICAO;
    
    @Column(name = "nom", nullable = false, length = 200)
    private String nom;
    
    @Column(name = "ville", nullable = false, length = 100)
    private String ville;
    
    @Column(name = "pays", nullable = false, length = 100)
    private String pays;
    
    @Column(name = "capacite_passagers")
    private Integer capacitePassagers; // Capacité journalière
    
    @Column(name = "nombre_pistes")
    private Integer nombrePistes;
    
    @Column(name = "fuseau_horaire", length = 50)
    private String fuseauHoraire;
    
    @Column(name = "latitude")
    private Double latitude;
    
    @Column(name = "longitude")
    private Double longitude;
    
    @Column(name = "altitude")
    private Integer altitude; // en mètres
    
    @Enumerated(EnumType.STRING)
    @Column(name = "type_aeroport")
    private TypeAeroport typeAeroport = TypeAeroport.INTERNATIONAL;
    
    @Column(name = "telephone", length = 20)
    private String telephone;
    
    @Column(name = "email", length = 100)
    private String email;
    
    @Column(name = "site_web", length = 255)
    private String siteWeb;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "statut", nullable = false)
    private StatutAeroport statut = StatutAeroport.OPERATIONNEL;
    
    @OneToMany(mappedBy = "aeroportDepart", cascade = CascadeType.ALL)
    @ToString.Exclude
    private Set<Vol> volsDepart = new HashSet<>();
    
    @OneToMany(mappedBy = "aeroportArrivee", cascade = CascadeType.ALL)
    @ToString.Exclude
    private Set<Vol> volsArrivee = new HashSet<>();
    
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
    public void addVolDepart(Vol vol) {
        this.volsDepart.add(vol);
        vol.setAeroportDepart(this);
    }
    
    public void removeVolDepart(Vol vol) {
        this.volsDepart.remove(vol);
        vol.setAeroportDepart(null);
    }
    
    public void addVolArrivee(Vol vol) {
        this.volsArrivee.add(vol);
        vol.setAeroportArrivee(this);
    }
    
    public void removeVolArrivee(Vol vol) {
        this.volsArrivee.remove(vol);
        vol.setAeroportArrivee(null);
    }
    
    public String getNomComplet() {
        return nom + " (" + codeIATA + ")";
    }
    
    public boolean isOperationnel() {
        return this.statut == StatutAeroport.OPERATIONNEL;
    }
}
