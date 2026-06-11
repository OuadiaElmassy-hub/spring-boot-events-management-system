package com.ouadia.rovista1.services.admin;

import com.ouadia.rovista1.dtos.admin.AdminDetailedStatsDTO;
import com.ouadia.rovista1.dtos.admin.AdminStatsDTO;
import com.ouadia.rovista1.entities.enums.StatutEvenement;
import com.ouadia.rovista1.entities.enums.StatutPaiement;
import com.ouadia.rovista1.repositories.EventRepository;
import com.ouadia.rovista1.repositories.ReservationRepository;
import com.ouadia.rovista1.repositories.UtilisateurRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AdminDashboardService {

    private final UtilisateurRepository userRepo;
    private final EventRepository eventRepo;
    private final ReservationRepository reservationRepo;

    public AdminStatsDTO getStats() {
        LocalDateTime startOfMonth = LocalDate.now()
            .withDayOfMonth(1).atStartOfDay();

        long totalUsers       = userRepo.count();
        long totalOrganizers  = userRepo.countByRole("ORGANISATEUR");
        long activeEvents     = eventRepo.countByStatutEvenement(StatutEvenement.APPROUVE);
        long pendingEvents    = eventRepo.countByStatutEvenement(StatutEvenement.EN_ATTENTE);
        long totalBookings    = reservationRepo.count();
        long newUsersThisMonth   = userRepo.countByCreatedAtAfter(startOfMonth);
        long bookingsThisMonth   = reservationRepo.countByDateReservationAfter(startOfMonth);
        Double totalRevenue      = eventRepo.totalRevenue(StatutPaiement.VALIDE);

        return AdminStatsDTO.builder()
                .totalUsers(totalUsers)
                .totalOrganizers(totalOrganizers)
                .activeEvents(activeEvents)
                .pendingEvents(pendingEvents)
                .totalBookings(totalBookings)
                .newUsersThisMonth(newUsersThisMonth)
                .bookingsThisMonth(bookingsThisMonth)
                .pendingOrganizers(0L)           // pendingOrganizers : à calculer si besoin
                .totalRevenue(totalRevenue)
                .revenueGrowth(null)          // revenueGrowth : comparaison avec mois dernier
                .build();
    }

    public AdminDetailedStatsDTO getDetailedStats() {
        // Revenus par catégorie
        Map<String, Double> revenueByCategory = new LinkedHashMap<>();
        for (Object[] row : eventRepo.revenueByCategorie(StatutPaiement.VALIDE)) {
            revenueByCategory.put((String) row[0], ((Number) row[1]).doubleValue());
        }

        // Événements par catégorie
        Map<String, Long> eventsByCategory = new LinkedHashMap<>();
        for (Object[] row : eventRepo.countByCategorie(StatutEvenement.APPROUVE)) {
            eventsByCategory.put((String) row[0], ((Number) row[1]).longValue());
        }

        // Top villes (top 4)
        List<AdminDetailedStatsDTO.VilleStatDTO> topCities = new ArrayList<>();
        for (Object[] row : eventRepo.topVilles(PageRequest.of(0, 4), StatutEvenement.APPROUVE)) {
            topCities.add( new AdminDetailedStatsDTO.VilleStatDTO(
                (String) row[0],
                ((Number) row[1]).longValue()
            ));
        }

        LocalDateTime startOfMonth = LocalDate.now().withDayOfMonth(1).atStartOfDay();

        return AdminDetailedStatsDTO.builder()
                .totalBookings(reservationRepo.count())
                .avgOccupancyRate(eventRepo.avgOccupancyRate(StatutEvenement.APPROUVE))
                .avgRevenuePerEvent(reservationRepo.avgRevenuePerEvenement(StatutPaiement.VALIDE))
                .eventsThisMonth(eventRepo.countByDateCreationAfterAndStatutEvenement(startOfMonth, StatutEvenement.APPROUVE))
                .revenueByCategory(revenueByCategory)
                .eventsByCategory(eventsByCategory)
                .topCities(topCities)
                .build();
    }
}