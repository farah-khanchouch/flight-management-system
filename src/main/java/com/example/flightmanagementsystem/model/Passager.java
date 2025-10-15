package com.example.flightmanagementsystem.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import com.example.flightmanagementsystem.model.enums.Genre;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "passagers")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Passager {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "nom", nullable = false, length = 100)
    private String nom;
    
    @Column(name = "prenom", nullable = false, length = 100)
    private String prenom;
    
    @Column(name = "numero_passeport", unique = true, length = 20)
    private String numeroPasseport;
    
    @Column(name = "numero_carte_identite", unique = true, length = 20)
    private String numeroCarteIdentite;
    
    @Column(name = "date_naissance", nullable = false)
    private LocalDate dateNaissance;
    
    @Column(name = "nationalite", nullable = false, length = 50)
    private String nationalite;
    
    @Column(name = "email", nullable = false, unique = true)
    private String email;
    
    @Column(name = "telephone", nullable = false, length = 20)
    private String telephone;
    
    @Column(name = "adresse", length = 255)
    private String adresse;
    
    @Column(name = "ville", length = 100)
    private String ville;
    
    @Column(name = "pays", length = 100)
    private String pays;
    
    @Column(name = "code_postal", length = 20)
    private String codePostal;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "genre")
    private Genre genre;
    
    @ManyToMany(mappedBy = "passagers", fetch = FetchType.LAZY)
    @ToString.Exclude
    private Set<Reservation> reservations = new HashSet<>();
    
    @OneToOne
    @JoinColumn(name = "user_id")
    @ToString.Exclude
    private User user;
    
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
    
    public void addReservation(Reservation reservation) {
        this.reservations.add(reservation);
        reservation.getPassagers().add(this);
    }
    
    public void removeReservation(Reservation reservation) {
        this.reservations.remove(reservation);
        reservation.getPassagers().remove(this);
    }
    
    public int getAge() {
        return LocalDate.now().getYear() - dateNaissance.getYear();
    }
}
