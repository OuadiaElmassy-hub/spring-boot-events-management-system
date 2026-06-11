package com.ouadia.rovista1.services.client;

import com.ouadia.rovista1.dtos.StatistiquesResponseDto;
import com.ouadia.rovista1.entities.Client;
import com.ouadia.rovista1.entities.enums.StatutReservation;
import com.ouadia.rovista1.repositories.ClientRepository;
import com.ouadia.rovista1.repositories.FavorieRepository;
import com.ouadia.rovista1.repositories.ReservationRepository;
import com.ouadia.rovista1.services.interfaces.IClientDashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ClientDashboardService implements IClientDashboardService {

    private final ReservationRepository reservationRepo;
    private final FavorieRepository favorieRepo;
    private final ClientRepository clientRepo;

    @Override
    public StatistiquesResponseDto getStats(Long ClientId) {
        Client client = clientRepo.findById(ClientId).orElseThrow();

        long totalBookings   = reservationRepo.countByClientId(ClientId);
        long eventsAttended  = reservationRepo
            .countByClientIdAndStatutAndEvenement_DateDebutBefore(
                ClientId,
                StatutReservation.CONFIRME,
                LocalDateTime.now()
            );
        long totalFavorites  = favorieRepo.countByClientId(ClientId);

        return StatistiquesResponseDto.builder()
                .nom(client.getNom())
                .email(client.getEmail())
                .prenom(client.getPrenom())
                .totalBookings(totalBookings)
                .eventsAttended(eventsAttended)
                .totalFavorites(totalFavorites)
                .build();
    }
}