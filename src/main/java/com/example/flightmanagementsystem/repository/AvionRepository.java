package com.example.flightmanagementsystem.repository;

import com.example.flightmanagementsystem.model.Avion;
import com.example.flightmanagementsystem.model.enums.StatutAvion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface AvionRepository extends JpaRepository<Avion, Long> {
    
    // Recherche par numéro de série
    Optional<Avion> findByNumeroSerie(String numeroSerie);
    
    // Vérifier si un avion existe par numéro de série
    boolean existsByNumeroSerie(String numeroSerie);
    
    // Recherche par type
    List<Avion> findByTypeAvion(String typeAvion);
    
    // Recherche par modèle
    List<Avion> findByModele(String modele);
    
    // Recherche par statut
    List<Avion> findByStatut(StatutAvion statut);
    
    // Avions opérationnels
    @Query("SELECT a FROM Avion a WHERE a.statut = 'OPERATIONNEL'")
    List<Avion> findAvionsOperationnels();
    
    // Avions disponibles (opérationnels)
    @Query("SELECT a FROM Avion a WHERE a.statut = 'OPERATIONNEL' " +
           "AND a.id NOT IN (SELECT v.avion.id FROM Vol v WHERE v.dateVol = CURRENT_DATE " +
           "AND v.statut IN ('PROGRAMME', 'EN_COURS', 'EMBARQUEMENT'))")
    List<Avion> findAvionsDisponibles();
    
    // Recherche par capacité minimale
    @Query("SELECT a FROM Avion a WHERE a.capacite >= :capaciteMin")
    List<Avion> findByCapaciteMinimum(@Param("capaciteMin") Integer capaciteMin);
    
    // Recherche par capacité entre min et max
    List<Avion> findByCapaciteBetween(Integer capaciteMin, Integer capaciteMax);
    
    // Recherche par année de fabrication
    List<Avion> findByAnneeFabrication(Integer anneeFabrication);
    
    // Avions nécessitant une maintenance
    @Query("SELECT a FROM Avion a WHERE a.prochaineMaintenace <= :date")
    List<Avion> findAvionsNecessitantMaintenance(@Param("date") LocalDateTime date);
    
    // Avions en maintenance
    @Query("SELECT a FROM Avion a WHERE a.statut = 'EN_MAINTENANCE'")
    List<Avion> findAvionsEnMaintenance();
    
    // Compter par statut
    long countByStatut(StatutAvion statut);
    
    // Compter par type
    long countByTypeAvion(String typeAvion);
    
    // Capacité totale de la flotte
    @Query("SELECT SUM(a.capacite) FROM Avion a WHERE a.statut = 'OPERATIONNEL'")
    Integer getTotalCapaciteFlotte();
    
    // Avions par type et statut
    List<Avion> findByTypeAvionAndStatut(String typeAvion, StatutAvion statut);
}
