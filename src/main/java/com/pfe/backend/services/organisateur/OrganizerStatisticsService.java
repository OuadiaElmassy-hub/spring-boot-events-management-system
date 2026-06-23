package com.pfe.backend.services.organisateur;

import com.pfe.backend.dtos.organisateur.OrgStatisticsDTO;
import com.pfe.backend.entities.Evenement;
import com.pfe.backend.entities.enums.StatutEvenement;
import com.pfe.backend.entities.enums.StatutPaiement;
import com.pfe.backend.entities.enums.StatutReservation;
import com.pfe.backend.repositories.EventRepository;
import com.pfe.backend.repositories.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true) // Best practice for optimization in read-only service methods
public class OrganizerStatisticsService {

    private final EventRepository eventRepo;
    private final ReservationRepository reservationRepo;

    public OrgStatisticsDTO getStatistics(Long orgId) {

        // 1. Safe Null Handling for Total Revenue
        Double rawRevenue = eventRepo.totalRevenueByOrganizer(orgId, StatutPaiement.VALIDE);
        Double totalRevenue = (rawRevenue != null) ? rawRevenue : 0.0;

        long totalParticipants = eventRepo.totalParticipantsByOrganizer(orgId, StatutEvenement.APPROUVE);
        long activeEvents      = eventRepo.countByOrganisateurIdAndStatutEvenement(orgId, StatutEvenement.APPROUVE);

        // 2. Fetch approved events (Make sure this query fetch-joins reservations to avoid N+1 issues!)
        List<Evenement> approvedEvents = eventRepo.findByOrganizerWithFilters(
                orgId, null, StatutEvenement.APPROUVE, PageRequest.of(0, 200)
        ).getContent();

        // 3. Taux de remplissage moyen (Calculated safely)
        double avgFillRate = approvedEvents.stream()
                .filter(e -> e.getCapacite() > 0)
                .mapToDouble(e -> {
                    long confirmedReservations = e.getReservations() != null
                            ? e.getReservations().stream()
                            .filter(r -> r.getStatut() == StatutReservation.CONFIRME)
                            .count()
                            : 0;
                    return (confirmedReservations * 100.0) / e.getCapacite();
                })
                .average()
                .orElse(0.0);
        Long totalReservations = approvedEvents.stream()
                .flatMap(e -> e.getReservations().stream())
                .filter(r -> r.getStatut() == StatutReservation.CONFIRME)
                .count();

        int totalCapacity = approvedEvents.stream()
                .mapToInt(Evenement::getCapacite)
                .sum();

        double fillRate = totalCapacity > 0
                ? (totalReservations * 100.0) / totalCapacity
                : 0.0;

        // 4. Revenus par événement
        List<OrgStatisticsDTO.RevenueItem> revenueByEvent =
                eventRepo.revenueByEvent(orgId, StatutPaiement.VALIDE).stream()
                        .map(row -> new OrgStatisticsDTO.RevenueItem(
                                (String) row[0],
                                row[1] != null ? ((Number) row[1]).doubleValue() : 0.0
                        )).toList();

        // 5. Remplissage par événement (Reusing the calculation cleanly)
        List<OrgStatisticsDTO.FillRateItem> fillRateByEvent =
                approvedEvents.stream().map(e -> {
                    int confirmedCount = (int) (e.getReservations() != null
                            ? e.getReservations().stream()
                            .filter(r -> r.getStatut() == StatutReservation.CONFIRME)
                            .count()
                            : 0);

                    // Avoid displaying events with invalid 0 capacity breaking UI expectations
                    int capacity = Math.max(e.getCapacite(), 0);

                    return new OrgStatisticsDTO.FillRateItem(
                            e.getTitre(), confirmedCount, capacity
                    );
                }).toList();

        // 6. Réservations par mois
        List<OrgStatisticsDTO.MonthItem> bookingsByMonth =
                reservationRepo.bookingsByMonth(orgId).stream()
                        .map(row -> new OrgStatisticsDTO.MonthItem(
                                (String) row[0],
                                row[1] != null ? ((Number) row[1]).longValue() : 0L
                        )).toList();

        return new OrgStatisticsDTO(
                avgFillRate,
                totalRevenue,
                totalParticipants,
            activeEvents,
            revenueByEvent,
            fillRateByEvent,
            bookingsByMonth
        );
    }
}