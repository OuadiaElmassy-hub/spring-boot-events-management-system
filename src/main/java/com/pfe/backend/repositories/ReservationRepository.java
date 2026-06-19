package com.pfe.backend.repositories;

import com.pfe.backend.entities.Client;
import com.pfe.backend.entities.Reservation;
import com.pfe.backend.entities.VisiteurInvite;
import com.pfe.backend.entities.enums.StatutPaiement;
import com.pfe.backend.entities.enums.StatutReservation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@Repository
public interface ReservationRepository extends JpaRepository<Reservation,Long> {
    List<Reservation> findByClient(Client client);
    List<Reservation> findByVisiteurInvite(VisiteurInvite visiteurInvite);
    boolean existsByClient(Client client);
    boolean existsByVisiteurInvite(VisiteurInvite visiteurInvite);

    Page<Reservation> findByClientIdOrderByDateReservationDesc(Long ClientId, Pageable pageable);

    Page<Reservation> findByClientIdAndStatutOrderByDateReservationDesc(
            Long clientId, StatutReservation statut, Pageable pageable);

    long countByClientId(Long clientId);

    // Événements passés et confirmés = "participés"
    long countByClientIdAndStatutAndEvenement_DateDebutBefore(
            Long clientId, StatutReservation statut, LocalDateTime dateDebut);

    // Pour le PDF
    Optional<Reservation> findByIdAndClientId(Long id, Long clientId);

    Page<Reservation> findByClientId(Long clientId, Pageable pageable);

    // pour le dashboard Admin : _________________________________

    long count();

    long countByDateReservationAfter(LocalDateTime date);

    @Query("SELECT COALESCE(AVG(e.prix), 0) FROM Reservation r JOIN r.paiement p JOIN r.evenement e WHERE p.statut = :statut")
    Double avgRevenuePerEvenement(@Param("statut")StatutPaiement statut);

    // pour le dashboard Organisateur : _________________________________

    // Réservations paginées pour les événements d'un organisateur

    @Query("""
        SELECT r FROM Reservation r
        JOIN r.evenement e
        WHERE e.organisateur.id = :orgId
        AND (:eventId IS NULL OR e.id = :eventId)
        AND (:statut  IS NULL OR r.statut = :statut)
        ORDER BY r.dateReservation DESC
    """)
    Page<Reservation> findByOrganizerWithFilters(
            @Param("orgId")   Long orgId,
            @Param("eventId") Long eventId,
            @Param("statut")  StatutReservation statut,
            Pageable pageable
    );

    // Revenus filtrés (pour l'affichage dans l'en-tête de la page)
    @Query("""
        SELECT COALESCE(SUM(e.prix), 0)
        FROM Reservation r
        JOIN r.evenement e
        WHERE e.organisateur.id = :orgId
        AND (:eventId IS NULL OR e.id = :eventId)
        AND r.statut = :statut
    """)
    Double totalRevenuByFilters(
            @Param("orgId")   Long orgId,
            @Param("eventId") Long eventId,
            @Param("statut") StatutReservation statut
    );

    // Réservations par mois sur les 6 derniers mois
//    @Query("""
//        SELECT DATE_FORMAT(r.dateReservation, '%b %Y') AS month,
//               COUNT(*) AS count
//        FROM reservation r
//        JOIN evenement e ON r.evenement.id = e.id
//        WHERE e.organisateur.id = :orgId
//        AND   r.dateReservation  >= DATE_SUB(NOW(), INTERVAL 6 MONTH)
//        GROUP BY DATE_FORMAT(r.dateReservation, '%Y-%m')
//        ORDER BY MIN(r.dateReservation)
//    """)
    // pour MySQL
    @Query(value = """
        SELECT DATE_FORMAT(r.date_reservation, '%Y-%m') AS month,
                           COUNT(*) AS count
                    FROM reservation r
                    JOIN evenement e ON r.evenement_id = e.id
                    WHERE e.organisateur_utilisateur_id = :orgId
                      AND r.date_reservation >= DATE_SUB(NOW(), INTERVAL 6 MONTH)
                    GROUP BY DATE_FORMAT(r.date_reservation, '%Y-%m')
                    ORDER BY DATE_FORMAT(r.date_reservation, '%Y-%m')
    """, nativeQuery = true)

    // pour H2
//    @Query(value = """
//        SELECT FORMATDATETIME(r.date_reservation, 'MMM yyyy') AS "month",
//               COUNT(*) AS "count"
//        FROM reservation r
//        JOIN evenement e ON r.evenement_id = e.id
//        WHERE e.ORGANISATEUR_UTILISATEUR_ID = :orgId
//        AND   r.date_reservation >= DATEADD('MONTH', -6, CURRENT_DATE)
//        GROUP BY FORMATDATETIME(r.date_reservation, 'yyyy-MM')
//        ORDER BY MIN(r.date_reservation)
//    """, nativeQuery = true)
    List<Object[]> bookingsByMonth(@Param("orgId") Long orgId);

    // Pour l'export : toutes les réservations sans pagination
    @Query("""
        SELECT r FROM Reservation r
        JOIN r.evenement e
        WHERE e.organisateur.id = :orgId
        AND (:eventId IS NULL OR e.id = :eventId)
        AND (:statut  IS NULL OR r.statut = :statut)
        ORDER BY r.dateReservation DESC
    """)
    List<Reservation> findAllForExport(
            @Param("orgId")   Long orgId,
            @Param("eventId") Long eventId,
            @Param("statut")  StatutReservation statut
    );
    // Vérifie si le client a une réservation confirmée pour cet événement
    boolean existsByClientIdAndEvenementIdAndStatut(
            Long clientId, Long evenementId, StatutReservation statut);
}
