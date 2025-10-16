package com.example.flightmanagementsystem.config;

import com.example.flightmanagementsystem.service.ReservationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class ScheduledTasks {
    
    private final ReservationService reservationService;
    
    /**
     * Traiter les réservations expirées toutes les heures
     */
    @Scheduled(cron = "0 0 * * * *") // Chaque heure
    public void traiterReservationsExpirees() {
        log.info("Début du traitement des réservations expirées");
        try {
            reservationService.traiterReservationsExpirees();
            log.info("Traitement des réservations expirées terminé");
        } catch (Exception e) {
            log.error("Erreur lors du traitement des réservations expirées", e);
        }
    }
    
    /**
     * Log des statistiques quotidiennes à minuit
     */
    @Scheduled(cron = "0 0 0 * * *") // Chaque jour à minuit
    public void logStatistiquesQuotidiennes() {
        log.info("=== Statistiques quotidiennes ===");
        try {
            Double revenue = reservationService.getTotalRevenue();
            log.info("Chiffre d'affaires total: {} EUR", revenue);
        } catch (Exception e) {
            log.error("Erreur lors du calcul des statistiques", e);
        }
    }
}
