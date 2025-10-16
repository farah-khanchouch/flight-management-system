package com.example.flightmanagementsystem.repository;

import com.example.flightmanagementsystem.model.Equipage;
import com.example.flightmanagementsystem.model.enums.FonctionEquipage;
import com.example.flightmanagementsystem.model.enums.StatutEquipage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface EquipageRepository extends JpaRepository<Equipage, Long> {
    
    // Recherche par numéro de licence
    Optional<Equipage> findByNumeroLicence(String numeroLicence);
    
    // Vérifier si licence existe
    boolean existsByNumeroLicence(String numeroLicence);
    
    // Recherche par email
    Optional<Equipage> findByEmail(String email);
    
    // Vérifier si email existe
    boolean existsByEmail(String email);
    
    // Recherche par fonction
    List<Equipage> findByFonction(FonctionEquipage fonction);
    
    // Recherche par statut
    List<Equipage> findByStatut(StatutEquipage statut);
    
    // Recherche par fonction et statut
    List<Equipage> findByFonctionAndStatut(FonctionEquipage fonction, StatutEquipage statut);
    
    // Pilotes actifs
    @Query("SELECT e FROM Equipage e WHERE e.fonction IN ('PILOTE', 'COPILOTE') " +
           "AND e.statut = 'ACTIF' AND e.dateExpirationLicence > CURRENT_DATE")
    List<Equipage> findPilotesActifs();
    
    // Personnel de cabine actif
    @Query("SELECT e FROM Equipage e WHERE e.fonction IN ('CHEF_CABINE', 'HOTESSE', 'STEWARD') " +
           "AND e.statut = 'ACTIF'")
    List<Equipage> findPersonnelCabineActif();
    
    // Membres d'équipage disponibles
    @Query("SELECT e FROM Equipage e WHERE e.statut = 'ACTIF' " +
           "AND e.dateExpirationLicence > CURRENT_DATE " +
           "AND e.id NOT IN (SELECT eq.id FROM Vol v JOIN v.equipages eq WHERE v.dateVol = :date)")
    List<Equipage> findEquipageDisponible(@Param("date") LocalDate date);
    
    // Membres d'équipage disponibles par fonction
    @Query("SELECT e FROM Equipage e WHERE e.fonction = :fonction " +
           "AND e.statut = 'ACTIF' " +
           "AND e.dateExpirationLicence > CURRENT_DATE " +
           "AND e.id NOT IN (SELECT eq.id FROM Vol v JOIN v.equipages eq WHERE v.dateVol = :date)")
    List<Equipage> findEquipageDisponibleByFonction(
        @Param("fonction") FonctionEquipage fonction,
        @Param("date") LocalDate date
    );
    
    // Recherche par nationalité
    List<Equipage> findByNationalite(String nationalite);
    
    // Recherche par nom
    @Query("SELECT e FROM Equipage e WHERE LOWER(e.nom) LIKE LOWER(CONCAT('%', :nom, '%'))")
    List<Equipage> searchByNom(@Param("nom") String nom);
    
    // Licences expirant bientôt
    @Query("SELECT e FROM Equipage e WHERE e.dateExpirationLicence BETWEEN :dateDebut AND :dateFin")
    List<Equipage> findLicencesExpirantBientot(
        @Param("dateDebut") LocalDate dateDebut,
        @Param("dateFin") LocalDate dateFin
    );
    
    // Licences expirées
    @Query("SELECT e FROM Equipage e WHERE e.dateExpirationLicence < CURRENT_DATE")
    List<Equipage> findLicencesExpirees();
    
    // Membres d'équipage d'un vol
    @Query("SELECT e FROM Equipage e JOIN e.vols v WHERE v.id = :volId")
    List<Equipage> findByVolId(@Param("volId") Long volId);
    
    // Compter par fonction
    long countByFonction(FonctionEquipage fonction);
    
    // Compter par statut
    long countByStatut(StatutEquipage statut);
    
    // Total heures de vol par équipage
    @Query("SELECT e, e.heuresVol FROM Equipage e WHERE e.fonction IN ('PILOTE', 'COPILOTE') " +
           "ORDER BY e.heuresVol DESC")
    List<Object[]> getHeuresVolParPilote();
    
    // Nouveaux membres (derniers 30 jours)
    @Query("SELECT e FROM Equipage e WHERE e.dateEmbauche >= :date ORDER BY e.dateEmbauche DESC")
    List<Equipage> findNouveauxMembres(@Param("date") LocalDate date);
}
