package com.example.flightmanagementsystem.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import com.example.flightmanagementsystem.model.enums.FonctionEquipage;
import com.example.flightmanagementsystem.model.enums.StatutEquipage;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "equipages")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Equipage {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "nom", nullable = false, length = 100)
    private String nom;
    
    @Column(name = "prenom", nullable = false, length = 100)
    private String prenom;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "fonction", nullable = false)
    private FonctionEquipage fonction;
    
    @Column(name = "numero_licence", unique = true, length = 50)
    private String numeroLicence;
    
    @Column(name = "nationalite", nullable = false, length = 50)
    private String nationalite;
    
    @Column(name = "date_naissance", nullable = false)
    private LocalDate dateNaissance;
    
    @Column(name = "email", unique = true, length = 100)
    private String email;
    
    @Column(name = "telephone", length = 20)
    private String telephone;
    
    @Column(name = "adresse", length = 255)
    private String adresse;
    
    @Column(name = "date_embauche")
    private LocalDate dateEmbauche;
    
    @Column(name = "heures_vol")
    private Integer heuresVol; // Total heures de vol
    
    @Column(name = "date_expiration_licence")
    private LocalDate dateExpirationLicence;
    
    @Column(name = "certifications", length = 500)
    private String certifications; // Liste des certifications séparées par des virgules
    
    @Enumerated(EnumType.STRING)
    @Column(name = "statut", nullable = false)
    private StatutEquipage statut = StatutEquipage.ACTIF;
    
    @ManyToMany(mappedBy = "equipages", fetch = FetchType.LAZY)
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
    public String getNomComplet() {
        return prenom + " " + nom;
    }
    
    public void addVol(Vol vol) {
        this.vols.add(vol);
        vol.getEquipages().add(this);
    }
    
    public void removeVol(Vol vol) {
        this.vols.remove(vol);
        vol.getEquipages().remove(this);
    }
    
    public boolean isPilote() {
        return this.fonction == FonctionEquipage.PILOTE || 
               this.fonction == FonctionEquipage.COPILOTE;
    }
    
    public boolean isLicenceValide() {
        if (dateExpirationLicence == null) {
            return false;
        }
        return LocalDate.now().isBefore(dateExpirationLicence);
    }
    
    public boolean isDisponible() {
        return this.statut == StatutEquipage.ACTIF && isLicenceValide();
    }
}
