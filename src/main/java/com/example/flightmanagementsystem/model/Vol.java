package com.example.flightmanagementsystem.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import com.example.flightmanagementsystem.model.enums.StatutVol;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "vols", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"code_iata_transporteur", "numero_vol", "date_vol"})
})
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Vol {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "code_iata_transporteur", nullable = false, length = 3)
    private String codeIATATransporteur;
    
    @Column(name = "numero_vol", nullable = false, length = 4)
    private String numeroVol;
    
    @Column(name = "date_vol", nullable = false)
    private LocalDate dateVol;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "aeroport_depart_id", nullable = false)
    @ToString.Exclude
    private Aeroport aeroportDepart;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "aeroport_arrivee_id", nullable = false)
    @ToString.Exclude
    private Aeroport aeroportArrivee;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "avion_id", nullable = false)
    @ToString.Exclude
    private Avion avion;
    
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "vol_equipage",
        joinColumns = @JoinColumn(name = "vol_id"),
        inverseJoinColumns = @JoinColumn(name = "equipage_id")
    )
    @ToString.Exclude
    private Set<Equipage> equipages = new HashSet<>();
    
    @OneToMany(mappedBy = "vol", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private Set<Reservation> reservations = new HashSet<>();
    
    @Column(name = "heure_depart")
    private LocalTime heureDepart;
    
    @Column(name = "heure_arrivee")
    private LocalTime heureArrivee;
    
    @Column(name = "duree_vol")
    private Integer dureeVol; // en minutes
    
    @Column(name = "prix_base")
    private Double prixBase;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "statut", nullable = false)
    private StatutVol statut = StatutVol.PROGRAMME;
    
    @Column(name = "places_disponibles")
    private Integer placesDisponibles;
    
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
        if (avion != null && placesDisponibles == null) {
            placesDisponibles = avion.getCapacite();
        }
    }
    
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
    
    // Helper methods
    public String getCodeVol() {
        return codeIATATransporteur + numeroVol;
    }
    
    public void addEquipage(Equipage equipage) {
        this.equipages.add(equipage);
        equipage.getVols().add(this);
    }
    
    public void removeEquipage(Equipage equipage) {
        this.equipages.remove(equipage);
        equipage.getVols().remove(this);
    }
    
    public void addReservation(Reservation reservation) {
        this.reservations.add(reservation);
        reservation.setVol(this);
    }
    
    public void removeReservation(Reservation reservation) {
        this.reservations.remove(reservation);
        reservation.setVol(null);
    }
}
