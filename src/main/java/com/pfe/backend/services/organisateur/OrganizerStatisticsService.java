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

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrganizerStatisticsService {

    private final EventRepository eventRepo;
    private final ReservationRepository reservationRepo;

    public OrgStatisticsDTO getStatistics(Long orgId) {

        Double totalRevenue      = eventRepo.totalRevenueByOrganizer(orgId, StatutPaiement.VALIDE);
        long   totalParticipants = eventRepo.totalParticipantsByOrganizer(orgId, StatutEvenement.APPROUVE);
        long   activeEvents      = eventRepo.countByOrganisateurIdAndStatutEvenement(orgId, StatutEvenement.APPROUVE);

        // Taux de remplissage moyen
        List<Evenement> approvedEvents = eventRepo.findByOrganizerWithFilters(
            orgId, null, StatutEvenement.APPROUVE, PageRequest.of(0, 200)
        ).getContent();

        double avgFillRate = approvedEvents.stream()
            .filter(e -> e.getCapacite() > 0)
            .mapToDouble(e -> {
                long p = e.getReservations() != null
                    ? e.getReservations().stream()
                        .filter(r -> r.getStatut() == StatutReservation.CONFIRME)
                        .count()
                    : 0;
                return (p * 100.0) / e.getCapacite();
            })
            .average().orElse(0.0);

        // Revenus par événement
        List<OrgStatisticsDTO.RevenueItem> revenueByEvent =
            eventRepo.revenueByEvent(orgId, StatutPaiement.VALIDE).stream()
                .map(row -> new OrgStatisticsDTO.RevenueItem(
                    (String) row[0],
                    ((Number) row[1]).doubleValue()
                )).toList();

        // Remplissage par événement
        List<OrgStatisticsDTO.FillRateItem> fillRateByEvent =
            approvedEvents.stream().map(e -> {
                int p = (int) (e.getReservations() != null
                    ? e.getReservations().stream()
                        .filter(r -> r.getStatut() == StatutReservation.CONFIRME)
                        .count()
                    : 0);
                return new OrgStatisticsDTO.FillRateItem(
                    e.getTitre(), p, e.getCapacite() != 0 ? e.getCapacite() : 0
                );
            }).toList();

        // Réservations par mois (6 derniers mois)
        List<OrgStatisticsDTO.MonthItem> bookingsByMonth =
            reservationRepo.bookingsByMonth(orgId).stream()
                .map(row -> new OrgStatisticsDTO.MonthItem(
                    (String) row[0],
                    ((Number) row[1]).longValue()
                )).toList();
        // Dans votre service
//        List<Map<String, Object>> bookingsByMonth = rawResults.stream()
//                .map(row -> Map.of(
//                        "month", row[0],
//                        "count", row[1]
//                ))
//                .collect(Collectors.toList());

        return OrgStatisticsDTO.builder()
                .totalRevenue(totalRevenue)
                .totalParticipants(totalParticipants)
                .avgFillRate(avgFillRate)
                .activeEvents(activeEvents)
                .revenueByEvent(revenueByEvent)
                .fillRateByEvent(fillRateByEvent)
                .bookingsByMonth(bookingsByMonth)
                .build();
    }
}