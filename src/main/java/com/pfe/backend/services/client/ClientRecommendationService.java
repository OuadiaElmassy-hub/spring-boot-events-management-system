package com.pfe.backend.services.client;

import com.pfe.backend.dtos.RecommendationResponseDto;
import com.pfe.backend.entities.Evenement;
import com.pfe.backend.entities.enums.StatutEvenement;
import com.pfe.backend.entities.enums.StatutReservation;
import com.pfe.backend.repositories.EventRepository;
import com.pfe.backend.repositories.FavorieRepository;
import com.pfe.backend.repositories.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClientRecommendationService {

    private final EventRepository eventRepo;
    private final FavorieRepository favorieRepo;
    private final ReservationRepository reservationRepo;

    // Poids relatifs des composantes du score (somme = 100 pour rester sur une échelle 0-99 lisible)
    private static final int POIDS_DATE       = 50;
    private static final int POIDS_VILLE      = 25;
    private static final int POIDS_CATEGORIE  = 25;


    public Page<RecommendationResponseDto> getRecommendations(Long clientId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Evenement> events = eventRepo
                .findRecommendationsForClient(clientId, LocalDateTime.now(), StatutReservation.ANNULEE, StatutEvenement.APPROUVE, pageable);

        // Récupérer les ids favoris de l'utilisateur dans cette page
        List<Long> eventIds = events.map(Evenement::getId).getContent();
        List<Long> favIds   = favorieRepo
                .findByClientIdAndEvenementIdIn(clientId, eventIds)
                .stream().map(f -> f.getEvenement().getId()).toList();

        // Profil de préférences du client : ville et catégorie les plus fréquentes
        // dans son historique de réservations (toutes réservations confondues, hors annulées).
        String villePreferee     = resolveVillePreferee(clientId);
        String categoriePreferee = resolveCategoriePreferee(clientId);

        return events.map(e -> RecommendationResponseDto.builder()
                        .id(e.getId())
                        .titre(e.getTitre())
                        .date(e.getDateDebut().toString())
                        .lieu(e.getVille())
                        .prix(e.getPrix())
                .score(computeScore(e, villePreferee, categoriePreferee))
                .isFavorite(favIds.contains(e.getId()))
                .build());
    }

    // ── Score basé sur 3 composantes : proximité de la date, affinité de ville, affinité de catégorie ──
    private int computeScore(Evenement e, String villePreferee, String categoriePreferee) {

        // 1. Composante temporelle : plus l'événement est proche, plus le score est élevé.
        long daysUntil = ChronoUnit.DAYS.between(LocalDate.now(), e.getDateDebut().toLocalDate());
        double scoreDate = Math.min(POIDS_DATE, Math.max(POIDS_DATE / 2.0, POIDS_DATE - (daysUntil / 10.0)));

        // 2. Composante ville : bonus complet si la ville de l'événement correspond
        // à la ville la plus fréquente dans l'historique du client.
        double scoreVille = (villePreferee != null
                && e.getVille() != null
                && e.getVille().equalsIgnoreCase(villePreferee))
                ? POIDS_VILLE
                : 0;

        // 3. Composante catégorie : bonus complet si la catégorie de l'événement correspond
        // à la catégorie la plus fréquente dans l'historique du client.
        double scoreCategorie = (categoriePreferee != null
                && e.getCategorie() != null
                && e.getCategorie().getNom() != null
                && e.getCategorie().getNom().equalsIgnoreCase(categoriePreferee))
                ? POIDS_CATEGORIE
                : 0;

        int total = (int) Math.round(scoreDate + scoreVille + scoreCategorie);
        return Math.min(99, Math.max(0, total));
    }

    // ── Détermine la ville la plus fréquente dans l'historique de réservations du client ──
    private String resolveVillePreferee(Long clientId) {
        List<Evenement> evenements = getEvenementsReserves(clientId);
        return evenements.stream()
                .map(Evenement::getVille)
                .filter(Objects::nonNull)
                .collect(Collectors.groupingBy(v -> v, Collectors.counting()))
                .entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(null);
    }

    // ── Détermine la catégorie la plus fréquente dans l'historique de réservations du client ──
    private String resolveCategoriePreferee(Long clientId) {
        List<Evenement> evenements = getEvenementsReserves(clientId);
        return evenements.stream()
                .map(Evenement::getCategorie)
                .filter(Objects::nonNull)
                .map(c -> c.getNom())
                .filter(Objects::nonNull)
                .collect(Collectors.groupingBy(n -> n, Collectors.counting()))
                .entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(null);
    }

    private List<Evenement> getEvenementsReserves(Long clientId) {
        return reservationRepo.findByClientId(clientId, PageRequest.of(0, 200))
                .stream()
                .map(r -> r.getEvenement())
                .filter(Objects::nonNull)
                .toList();
    }
}