package com.example.flightmanagementsystem.repository;

import com.example.flightmanagementsystem.model.Vol;
import com.example.flightmanagementsystem.model.enums.StatutVol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface VolRepository extends JpaRepository<Vol, Long> {
    
    // Recherche par code vol (IATA + numéro)
    @Query("SELECT v FROM Vol v WHERE v.codeIATATransporteur = :codeIATA AND v.numeroVol = :numeroVol")
    List<Vol> findByCodeVol(@Param("codeIATA") String codeIATA, @Param("numeroVol") String numeroVol);
    
    // Recherche par code vol et date
    Optional<Vol> findByCodeIATATransporteurAndNumeroVolAndDateVol(
        String codeIATATransporteur, 
        String numeroVol, 
        LocalDate dateVol
    );
    
    // Recherche par date
    List<Vol> findByDateVol(LocalDate dateVol);
    
    // Recherche par période
    List<Vol> findByDateVolBetween(LocalDate dateDebut, LocalDate dateFin);
    
    // Recherche par aéroport de départ
    @Query("SELECT v FROM Vol v WHERE v.aeroportDepart.id = :aeroportId")
    List<Vol> findByAeroportDepart(@Param("aeroportId") Long aeroportId);
    
    // Recherche par aéroport d'arrivée
    @Query("SELECT v FROM Vol v WHERE v.aeroportArrivee.id = :aeroportId")
    List<Vol> findByAeroportArrivee(@Param("aeroportId") Long aeroportId);
    
    // Recherche par route (départ -> arrivée)
    @Query("SELECT v FROM Vol v WHERE v.aeroportDepart.id = :departId AND v.aeroportArrivee.id = :arriveeId")
    List<Vol> findByRoute(@Param("departId") Long departId, @Param("arriveeId") Long arriveeId);
    
    // Recherche par route et date
    @Query("SELECT v FROM Vol v WHERE v.aeroportDepart.id = :departId " +
           "AND v.aeroportArrivee.id = :arriveeId AND v.dateVol = :date")
    List<Vol> findByRouteAndDate(
        @Param("departId") Long departId, 
        @Param("arriveeId") Long arriveeId,
        @Param("date") LocalDate date
    );
    
    // Recherche par avion
    @Query("SELECT v FROM Vol v WHERE v.avion.id = :avionId")
    List<Vol> findByAvion(@Param("avionId") Long avionId);
    
    // Recherche par statut
    List<Vol> findByStatut(StatutVol statut);
    
    // Recherche par statut et date
    List<Vol> findByStatutAndDateVol(StatutVol statut, LocalDate dateVol);
    
    // Vols disponibles (avec places)
    @Query("SELECT v FROM Vol v WHERE v.placesDisponibles > 0 AND v.dateVol >= :date")
    List<Vol> findVolsDisponibles(@Param("date") LocalDate date);
    
    // Vols avec places disponibles pour une route
    @Query("SELECT v FROM Vol v WHERE v.aeroportDepart.id = :departId " +
           "AND v.aeroportArrivee.id = :arriveeId " +
           "AND v.placesDisponibles >= :nbPlaces " +
           "AND v.dateVol = :date")
    List<Vol> findVolsDisponiblesParRoute(
        @Param("departId") Long departId,
        @Param("arriveeId") Long arriveeId,
        @Param("nbPlaces") Integer nbPlaces,
        @Param("date") LocalDate date
    );
    
    // Vérifier si un vol existe
    boolean existsByCodeIATATransporteurAndNumeroVolAndDateVol(
        String codeIATATransporteur,
        String numeroVol,
        LocalDate dateVol
    );
    
    // Compter vols par statut
    long countByStatut(StatutVol statut);
    
    // Vols d'aujourd'hui
    @Query("SELECT v FROM Vol v WHERE v.dateVol = CURRENT_DATE")
    List<Vol> findVolsAujourdhui();
    
    // Vols retardés
    @Query("SELECT v FROM Vol v WHERE v.statut = 'RETARDE' AND v.dateVol = :date")
    List<Vol> findVolsRetardes(@Param("date") LocalDate date);
}
