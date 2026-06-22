package com.pfe.backend.services.organisateur;

import com.pfe.backend.dtos.organisateur.OrgDashboardStatsDTO;
import com.pfe.backend.entities.Organisateur;
import com.pfe.backend.entities.enums.StatutEvenement;
import com.pfe.backend.entities.enums.StatutPaiement;
import com.pfe.backend.entities.enums.StatutReservation;
import com.pfe.backend.exceptions.OrganisateurNotFoundException;
import com.pfe.backend.repositories.EventRepository;
import com.pfe.backend.repositories.OrganisateurRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class OrganizerDashboardService {

    private final EventRepository eventRepo;
    private final OrganisateurRepository organisateurRepository;

    public OrgDashboardStatsDTO getStats(Long orgId) throws OrganisateurNotFoundException {
        Organisateur op = organisateurRepository.findById(orgId)
                .orElseThrow(() -> new OrganisateurNotFoundException("Organizer not found with id : "+orgId));

        LocalDateTime startOfMonth = LocalDate.now().withDayOfMonth(1).atStartOfDay();
        LocalDateTime startOfWeek  = LocalDate.now()
            .with(java.time.DayOfWeek.MONDAY).atStartOfDay();
        LocalDateTime lastMonthStart = startOfMonth.minusMonths(1);

        long   activeEvents          = eventRepo.countByOrganisateurIdAndStatutEvenement(orgId, StatutEvenement.APPROUVE);
        long   newEventsThisMonth    = eventRepo.countByOrganisateurIdAndDateCreationAfter(orgId, startOfMonth);
        long   totalParticipants     = eventRepo.totalParticipantsByOrganizer(orgId, StatutEvenement.APPROUVE);
        long   newParticipantsWeek   = eventRepo.newParticipantsThisWeek(orgId, startOfWeek, StatutReservation.CONFIRME);
        Double totalRevenue          = eventRepo.totalRevenueByOrganizer(orgId, StatutPaiement.VALIDE);
        Double revenueThisMonth      = eventRepo.revenueThisMonth(orgId, startOfMonth, StatutPaiement.VALIDE);
        Double revenueLastMonth      = eventRepo.revenueThisMonth(orgId, lastMonthStart, StatutPaiement.VALIDE);

        // Taux de croissance revenus
        Double revenueGrowth = null;
        if (revenueLastMonth != null && revenueLastMonth > 0 && revenueThisMonth != null) {
            revenueGrowth = Math.round(
                ((revenueThisMonth - revenueLastMonth) / revenueLastMonth) * 100.0
            ) * 1.0;
        }

        // Taux de remplissage moyen
        Double avgFillRate = computeAvgFillRate(orgId);


        return new OrgDashboardStatsDTO(
            op.getNom(),
            activeEvents,
            newEventsThisMonth,
            totalParticipants,
            newParticipantsWeek,
            totalRevenue,
            revenueGrowth,
            avgFillRate
        );
    }

    private Double computeAvgFillRate(Long orgId) {
        return eventRepo.findByOrganizerWithFilters(orgId, null, StatutEvenement.APPROUVE,
                        PageRequest.of(0, 100))
                .getContent().stream()
                .filter(e -> e.getCapacite() != 0 && e.getCapacite() > 0)
                .mapToDouble(e -> {
                    long participants = e.getReservations() != null
                            ? e.getReservations().stream()
                            .filter(r -> r.getStatut() == StatutReservation.CONFIRME)
                            .count()
                            : 0;
                    return (participants * 100.0) / e.getCapacite();
                })
                .average().orElse(0.0);
    }
}
