package com.example.flightmanagementsystem.repository;

import com.example.flightmanagementsystem.model.Reservation;
import com.example.flightmanagementsystem.model.enums.StatutReservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    
    // Recherche par numéro de réservation
    Optional<Reservation> findByNumeroReservation(String numeroReservation);
    
    // Vérifier si réservation existe
    boolean existsByNumeroReservation(String numeroReservation);
    
    // Recherche par utilisateur
    @Query("SELECT r FROM Reservation r WHERE r.user.id = :userId ORDER BY r.dateReservation DESC")
    List<Reservation> findByUserId(@Param("userId") Long userId);
    
    // Recherche par utilisateur et statut
    @Query("SELECT r FROM Reservation r WHERE r.user.id = :userId AND r.statut = :statut")
    List<Reservation> findByUserIdAndStatut(@Param("userId") Long userId, @Param("statut") StatutReservation statut);
    
    // Recherche par vol
    @Query("SELECT r FROM Reservation r WHERE r.vol.id = :volId")
    List<Reservation> findByVolId(@Param("volId") Long volId);
    
    // Recherche par vol et statut
    @Query("SELECT r FROM Reservation r WHERE r.vol.id = :volId AND r.statut = :statut")
    List<Reservation> findByVolIdAndStatut(@Param("volId") Long volId, @Param("statut") StatutReservation statut);
    
    // Recherche par statut
    List<Reservation> findByStatut(StatutReservation statut);
    
    // Réservations confirmées
    @Query("SELECT r FROM Reservation r WHERE r.statut = 'CONFIRMEE'")
    List<Reservation> findReservationsConfirmees();
    
    // Réservations en attente
    @Query("SELECT r FROM Reservation r WHERE r.statut = 'EN_ATTENTE'")
    List<Reservation> findReservationsEnAttente();
    
    // Réservations par période
    List<Reservation> findByDateReservationBetween(LocalDateTime dateDebut, LocalDateTime dateFin);
    
    // Réservations d'un passager
    @Query("SELECT r FROM Reservation r JOIN r.passagers p WHERE p.id = :passagerId")
    List<Reservation> findByPassagerId(@Param("passagerId") Long passagerId);
    
    // Réservations récentes (derniers 30 jours)
    @Query("SELECT r FROM Reservation r WHERE r.dateReservation >= :date ORDER BY r.dateReservation DESC")
    List<Reservation> findReservationsRecentes(@Param("date") LocalDateTime date);
    
    // Réservations expirées (en attente depuis plus de 24h)
    @Query("SELECT r FROM Reservation r WHERE r.statut = 'EN_ATTENTE' " +
           "AND r.dateReservation < :date")
    List<Reservation> findReservationsExpirees(@Param("date") LocalDateTime date);
    
    // Compter réservations par vol
    @Query("SELECT COUNT(r) FROM Reservation r WHERE r.vol.id = :volId")
    long countByVolId(@Param("volId") Long volId);
    
    // Compter réservations par utilisateur
    @Query("SELECT COUNT(r) FROM Reservation r WHERE r.user.id = :userId")
    long countByUserId(@Param("userId") Long userId);
    
    // Compter par statut
    long countByStatut(StatutReservation statut);
    
    // Chiffre d'affaires total
    @Query("SELECT SUM(r.prixTotal) FROM Reservation r WHERE r.statut = 'CONFIRMEE'")
    Double getTotalRevenue();
    
    // Chiffre d'affaires par période
    @Query("SELECT SUM(r.prixTotal) FROM Reservation r WHERE r.statut = 'CONFIRMEE' " +
           "AND r.dateReservation BETWEEN :dateDebut AND :dateFin")
    Double getRevenueByPeriod(@Param("dateDebut") LocalDateTime dateDebut, 
                              @Param("dateFin") LocalDateTime dateFin);
    
    // Statistiques - Réservations par mois
    @Query("SELECT MONTH(r.dateReservation), COUNT(r), SUM(r.prixTotal) " +
           "FROM Reservation r WHERE r.statut = 'CONFIRMEE' " +
           "GROUP BY MONTH(r.dateReservation)")
    List<Object[]> getStatistiquesParMois();
    
    // Top utilisateurs (par nombre de réservations)
    @Query("SELECT r.user, COUNT(r) FROM Reservation r " +
           "WHERE r.statut = 'CONFIRMEE' " +
           "GROUP BY r.user ORDER BY COUNT(r) DESC")
    List<Object[]> getTopUsers();
}
