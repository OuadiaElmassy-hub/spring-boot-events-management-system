package com.pfe.backend.services.client;

import com.pfe.backend.dtos.StatistiquesResponseDto;
import com.pfe.backend.entities.Client;
import com.pfe.backend.entities.enums.StatutReservation;
import com.pfe.backend.exceptions.ClientNotFoundException;
import com.pfe.backend.repositories.ClientRepository;
import com.pfe.backend.repositories.FavorieRepository;
import com.pfe.backend.repositories.ReservationRepository;
import com.pfe.backend.services.interfaces.IClientDashboardService;
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
    public StatistiquesResponseDto getStats(Long ClientId) throws ClientNotFoundException {
        Client client = clientRepo.findById(ClientId).orElseThrow(()->new ClientNotFoundException("Client not found"));

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