package com.ouadia.rovista1.repositories;
import com.ouadia.rovista1.dtos.evenement.EvenementResponseDto;
import com.ouadia.rovista1.entities.Evenement;
import com.ouadia.rovista1.entities.enums.StatutEvenement;
import com.ouadia.rovista1.entities.enums.StatutPaiement;
import com.ouadia.rovista1.entities.enums.StatutReservation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface EventRepository extends JpaRepository<Evenement,Long>,
        JpaSpecificationExecutor<Evenement> {

    // version 2

    Page<Evenement> findByStatutEvenement(StatutEvenement statutEvenement, Pageable pageable);
    List<Evenement> findByStatutEvenement(StatutEvenement statut);
    Page<Evenement> findByCategorieIdAndStatutEvenement(Long categorieId, StatutEvenement statutEvenement, Pageable pageable);
    List<Evenement> findByOrganisateurId(Long organisateurId);

    Page<Evenement> findByVilleIgnoreCase(String ville, Pageable pageable);
    List<Evenement> findByLieuSpecifique(String lieu);
    List<Evenement> findByPrix(double prix);
    List<Evenement> findByTitre(String titre);
    List<Evenement> findByDescription(String description);

    // Recommandations : events approuvés, pas déjà réservés par l'user, triés par date
    @Query("""
        SELECT e FROM Evenement e
        WHERE e.statutEvenement = :statutEv
        AND e.dateDebut > :now
        AND e.id NOT IN (
            SELECT r.evenement.id FROM Reservation r
            WHERE r.client.id = :clientId
            AND r.statut <> :statutR
        )
        ORDER BY e.dateDebut  ASC
    """)

    Page<Evenement> findRecommendationsForClient(
            @Param("clientId") Long clientId,
            @Param("now") LocalDateTime now,
            @Param("statutR") StatutReservation statutR,
            @Param("statutEv") StatutEvenement statutEv,
            Pageable pageable
    );


    // ________________________________________

    @Query("SELECT DISTINCT e FROM Evenement e LEFT JOIN e.categorie c WHERE" +
            " LOWER(e.titre) LIKE LOWER(CONCAT('%', :keyword, '%')) OR" +
            " LOWER(e.description) LIKE LOWER(CONCAT('%', :keyword, '%')) OR" +
            " LOWER(e.lieuSpecifique) LIKE LOWER(CONCAT('%', :keyword, '%')) OR" +
            " LOWER(c.nom) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Evenement> searchEvents(String keyword);

    Evenement findByIdAndStatutEvenement(Long id, StatutEvenement statutEvenement);

    // Pour le dashboard Admin : ____________________________________

    @Query("""
        SELECT e FROM Evenement e
        WHERE (:search    IS NULL OR LOWER(e.titre) LIKE LOWER(CONCAT('%',:search,'%'))
                                  OR LOWER(e.organisateur.nom) LIKE LOWER(CONCAT('%',:search,'%')))
        AND   (:status    IS NULL OR e.statutEvenement    = :status)
        AND   (:categorie IS NULL OR e.categorie.nom = :categorie)
        AND   (:ville     IS NULL OR e.ville     = :ville)
    """)
    Page<Evenement> searchAdmin(
            @Param("search")    String search,
            @Param("status")    StatutEvenement status,
            @Param("categorie") String categorie,
            @Param("ville")     String ville,
            Pageable pageable
    );

    long countByStatutEvenement(StatutEvenement status);

    long countByDateCreationAfter(LocalDateTime date);

    // Revenus totaux par catégorie
    @Query("""
        SELECT e.categorie.nom, SUM(r.evenement.prix * 1)
        FROM Reservation r
        JOIN r.evenement e
        WHERE r.paiement.statut = :statut
        GROUP BY e.categorie.nom
    """)
    List<Object[]> revenueByCategorie(@Param("statut") StatutPaiement statut);

    @Query("""
        SELECT e.categorie.nom, COUNT(e)
        FROM Evenement e
        WHERE e.statutEvenement = :statut
        GROUP BY e.categorie.nom
    """)
    List<Object[]> countByCategorie(@Param("statut") StatutPaiement statut);

    @Query("""
        SELECT e.ville, COUNT(e)
        FROM Evenement e
        WHERE e.statutEvenement = :statut
        GROUP BY e.ville
        ORDER BY COUNT(e) DESC
    """)
    List<Object[]> topVilles(Pageable pageable, @Param("statut") StatutPaiement statut);

    // Revenus globaux
    @Query("""
        SELECT COALESCE(SUM(e.prix), 0)
        FROM Reservation r JOIN r.evenement e
        WHERE r.paiement.statut = :statut
    """)
    Double totalRevenue(@Param("statut") StatutPaiement statut);

    // Taux remplissage moyen
    @Query("""
        SELECT AVG(CAST(SIZE(e.reservations) AS double) / e.capacite * 100)
        FROM Evenement e WHERE e.capacite > 0 AND e.statutEvenement = :statutE
    """)
    Double avgOccupancyRate(@Param("statutE") StatutEvenement statutE);

    Page<Evenement> findByOrderByDateCreationDesc(Pageable pageable);
    long countByDateCreationAfterAndStatutEvenement(LocalDateTime date, StatutEvenement status);

    long countByOrganisateurId(Long id);

    @Query("""
        SELECT COALESCE(SUM(e.prix), 0)
        FROM Reservation r JOIN r.evenement e
        WHERE r.paiement.statut = :statut
        AND e.organisateur.id = :id
    """)
    Double totalRevenueByOrganisateurId(@Param("id") Long id, @Param("statut") StatutPaiement statut);

    // ──  Pour le dashboard organisateur ─────────────────────

        // Recherche paginée avec filtres pour un organisateur donné
        @Query("""
        SELECT e FROM Evenement e
        WHERE e.organisateur.id = :orgId
        AND (:search IS NULL OR LOWER(e.titre) LIKE LOWER(CONCAT('%',:search,'%')))
        AND (:status IS NULL OR e.statutEvenement = :status)
        ORDER BY e.dateCreation DESC
    """)
        Page<Evenement> findByOrganizerWithFilters(
                @Param("orgId")  Long orgId,
                @Param("search") String search,
                @Param("status") StatutEvenement status,
                Pageable pageable
        );

        long countByOrganisateurIdAndStatutEvenement(Long orgId, StatutEvenement status);

        long countByOrganisateurIdAndDateCreationAfter(Long orgId, LocalDateTime date);

        // Revenus totaux de l'organisateur (réservations payées)
        @Query("""
        SELECT COALESCE(SUM(e.prix), 0)
        FROM Reservation r
        JOIN r.evenement e
        WHERE e.organisateur.id = :orgId
        AND   r.paiement.statut = :statut
    """)
        Double totalRevenueByOrganizer(@Param("orgId") Long orgId, @Param("statut") StatutPaiement statut);

        // Revenus du mois en cours
        @Query("""
        SELECT COALESCE(SUM(e.prix), 0)
        FROM Reservation r
        JOIN r.evenement e
        WHERE e.organisateur.id = :orgId
        AND   r.paiement.statut = :statut
        AND   r.dateReservation  >= :startOfMonth
    """)
        Double revenueThisMonth(
                @Param("orgId")        Long orgId,
                @Param("startOfMonth") LocalDateTime startOfMonth,
                @Param("statut") StatutPaiement statut
        );

        // Revenus par événement (pour le graphique)
        @Query("""
        SELECT e.titre, COALESCE(SUM(e.prix), 0)
        FROM Reservation r
        JOIN r.evenement e
        WHERE e.organisateur.id = :orgId
        AND   r.paiement.statut = :statut
        GROUP BY e.id, e.titre
        ORDER BY SUM(e.prix) DESC
    """)
        List<Object[]> revenueByEvent(
                @Param("orgId") Long orgId,
                @Param("statut") StatutPaiement statut);

        // Total participants pour l'organisateur
        @Query("""
        SELECT COALESCE(SUM(SIZE(e.reservations)), 0)
        FROM Evenement e
        WHERE e.organisateur.id = :orgId
        AND   e.statutEvenement = :statut
    """)
        long totalParticipantsByOrganizer(
                @Param("orgId") Long orgId,
                @Param("statut") StatutEvenement statut);

        // Nouveaux participants cette semaine
        @Query("""
        SELECT COUNT(r)
        FROM Reservation r
        JOIN r.evenement e
        WHERE e.organisateur.id = :orgId
        AND   r.dateReservation   >= :startOfWeek
        AND   r.statut      = :statut
    """)
        long newParticipantsThisWeek(
                @Param("orgId")       Long orgId,
                @Param("startOfWeek") LocalDateTime startOfWeek,
                @Param("statut") StatutReservation statut
        );

    Optional<Evenement> findByIdAndOrganisateurId(Long eventId, Long orgId);
}
