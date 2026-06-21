package com.pfe.backend.dtos.chat;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class ChatResponse {
    private String message;
    private List<?> events;
    private SearchCriteria criteresUtilises;
    private long total;

    // AJOUT : champ explicite pour que le frontend sache directement
    // s'il doit afficher "Voir les résultats →" ou non, sans avoir à
    // déduire ça lui-même via events == null || events.isEmpty()
    // (ce qui est facile à oublier de vérifier côté UI, comme on l'a vu
    // dans le screenshot où "Voir les résultats" s'affichait toujours).
    private boolean hasResults;

    // AJOUT : indique si la réponse est une recherche d'événements
    // ("recherche") ou une réponse conversationnelle libre ("conversation",
    // pour "bonjour", "merci", questions hors sujet, etc.). Permet au
    // frontend de ne jamais afficher le lien "Voir les résultats" ni
    // déclencher la navigation automatique sur une simple conversation.
    private String intent;
}