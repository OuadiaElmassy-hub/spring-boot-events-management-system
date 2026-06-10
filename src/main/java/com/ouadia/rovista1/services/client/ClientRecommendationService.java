package com.ouadia.rovista1.services.client;

import com.ouadia.rovista1.dtos.RecommendationResponseDto;
import com.ouadia.rovista1.entities.Evenement;
import com.ouadia.rovista1.entities.enums.StatutEvenement;
import com.ouadia.rovista1.entities.enums.StatutReservation;
import com.ouadia.rovista1.repositories.EventRepository;
import com.ouadia.rovista1.repositories.FavorieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ClientRecommendationService {

    private final EventRepository eventRepo;
    private final FavorieRepository favorieRepo;

    public Page<RecommendationResponseDto> getRecommendations(Long clientId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Evenement> events = eventRepo
                .findRecommendationsForClient(clientId, LocalDateTime.now(), StatutReservation.ANNULEE, StatutEvenement.APPROUVE, pageable);

        // Récupérer les ids favoris de l'utilisateur dans cette page
        List<Long> eventIds = events.map(Evenement::getId).getContent();
        List<Long> favIds   = favorieRepo
                .findByClientIdAndEvenementIdIn(clientId, eventIds)
                .stream().map(f -> f.getEvenement().getId()).toList();

        return events.map(e -> RecommendationResponseDto.builder()
                        .id(e.getId())
                        .titre(e.getTitre())
                        .date(e.getDateDebut().toString())
                        .lieu(e.getVille())
                        .prix(e.getPrix())
                        .score(computeScore(e, clientId))   // logique de scoring
                .isFavorite(favIds.contains(e.getId()))
                .build());
    }

    // Score simple basé sur la proximité de la date et la popularité
    private int computeScore(Evenement e, Long clientId) {
        long daysUntil = ChronoUnit.DAYS.between(LocalDate.now(), e.getDateDebut().toLocalDate());
        // Plus c'est proche → score plus élevé (plafonné à 99)
        return (int) Math.min(99, Math.max(50, 99 - (daysUntil / 10)));
    }
}