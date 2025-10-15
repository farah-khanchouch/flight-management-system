package com.example.flightmanagementsystem.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import com.example.flightmanagementsystem.model.enums.StatutReservation;
import com.example.flightmanagementsystem.model.enums.ClasseVol;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "reservations")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Reservation {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "numero_reservation", unique = true, nullable = false, length = 50)
    private String numeroReservation;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "vol_id", nullable = false)
    @ToString.Exclude
    private Vol vol;
    
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "reservation_passager",
        joinColumns = @JoinColumn(name = "reservation_id"),
        inverseJoinColumns = @JoinColumn(name = "passager_id")
    )
    @ToString.Exclude
    private Set<Passager> passagers = new HashSet<>();
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    @ToString.Exclude
    private User user;
    
    @Column(name = "date_reservation", nullable = false)
    private LocalDateTime dateReservation;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "statut", nullable = false)
    private StatutReservation statut = StatutReservation.EN_ATTENTE;
    
    @Column(name = "prix_total", nullable = false)
    private Double prixTotal;
    
    @Column(name = "nombre_passagers", nullable = false)
    private Integer nombrePassagers;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "classe", nullable = false)
    private ClasseVol classe = ClasseVol.ECONOMIQUE;
    
    @Column(name = "prix_unitaire")
    private Double prixUnitaire;
    
    @Column(name = "taxes")
    private Double taxes;
    
    @Column(name = "reduction")
    private Double reduction = 0.0;
    
    @Column(name = "commentaire", length = 500)
    private String commentaire;
    
    @Column(name = "date_paiement")
    private LocalDateTime datePaiement;
    
    @Column(name = "methode_paiement", length = 50)
    private String methodePaiement;
    
    @Column(name = "reference_paiement", length = 100)
    private String referencePaiement;
    
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    @PrePersist
    protected void onCreate() {
        if (numeroReservation == null) {
            numeroReservation = generateNumeroReservation();
        }
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
        dateReservation = LocalDateTime.now();
    }
    
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
    
    // Helper methods
    private String generateNumeroReservation() {
        return "RES-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }
    
    public void addPassager(Passager passager) {
        this.passagers.add(passager);
        passager.getReservations().add(this);
    }
    
    public void removePassager(Passager passager) {
        this.passagers.remove(passager);
        passager.getReservations().remove(this);
    }
    
    public void calculerPrixTotal() {
        if (prixUnitaire != null && nombrePassagers != null) {
            double montantBase = prixUnitaire * nombrePassagers;
            double montantTaxes = taxes != null ? taxes : 0.0;
            double montantReduction = reduction != null ? reduction : 0.0;
            this.prixTotal = montantBase + montantTaxes - montantReduction;
        }
    }
    
    public boolean isPaye() {
        return this.statut == StatutReservation.CONFIRMEE;
    }
    
    public boolean isAnnulee() {
        return this.statut == StatutReservation.ANNULEE;
    }
}
