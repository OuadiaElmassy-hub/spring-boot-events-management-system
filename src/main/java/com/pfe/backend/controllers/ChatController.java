// Controller corrigé -----------------------------------------------------------------------------------------
package com.pfe.backend.controllers;

import com.pfe.backend.dtos.chat.ChatResponse;
import com.pfe.backend.exceptions.OllamaUnavailableException;
import com.pfe.backend.security.JwtService;
import com.pfe.backend.services.ChatbotService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/chat")
@RequiredArgsConstructor
@CrossOrigin(origins = {"http://localhost:5173"})
public class ChatController {

    // CORRECTIF : même limite que côté service, vérifiée tôt pour éviter
    // un traitement inutile (extraction JWT, log, etc.) sur une requête
    // qui sera rejetée de toute façon.
    private static final int MAX_QUESTION_LENGTH = 500;

    private final ChatbotService chatbotService;
    private final JwtService jwtService;

    @PostMapping("/ask")
    public ResponseEntity<?> askChatbot(
            @RequestBody Map<String, String> payload,
            @RequestHeader(value = "Authorization", required = false) String authHeader
    ) {
        String question = payload.get("question");

        if (question == null || question.isBlank()) {
            return ResponseEntity.badRequest().body(Map.of(
                    "error", "Question vide."
            ));
        }

        // CORRECTIF : rejet explicite (400) d'une question trop longue
        // au lieu de la tronquer silencieusement plus loin dans le service.
        if (question.length() > MAX_QUESTION_LENGTH) {
            return ResponseEntity.badRequest().body(Map.of(
                    "error", "Question trop longue (max " + MAX_QUESTION_LENGTH + " caractères)."
            ));
        }

        String username = extractUsername(authHeader);
        log.info("Chatbot question: '{}' | user: {}", question,
                username != null ? username : "visiteur");

        try {
            ChatResponse response = chatbotService.ask(question, username);
            return ResponseEntity.ok(response);

        } catch (OllamaUnavailableException e) {
            // CORRECTIF : 503 dédié quand le problème vient du LLM externe,
            // plutôt qu'un 500 générique. Permet au frontend d'afficher
            // un message adapté ("le service IA est temporairement indisponible")
            // et facilite le monitoring (on distingue panne externe vs bug interne).
            log.error("Ollama indisponible : {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(Map.of(
                    "error", "Service IA temporairement indisponible. Réessayez dans un instant."
            ));

        } catch (Exception e) {
            // CORRECTIF : log.error avec la stack trace complète (e, pas seulement
            // e.getMessage()) pour faciliter le debug en prod.
            log.error("Erreur chatbot inattendue", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                    "error", "Service chatbot indisponible. Réessayez plus tard."
            ));
        }
    }

    private String extractUsername(String authHeader) {
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            try {
                return jwtService.extractUsername(authHeader.substring(7));
            } catch (Exception e) {
                log.warn("Token JWT invalide : {}", e.getMessage());
            }
        }
        return null;
    }
}
//package com.pfe.backend.controllers;
//
//import com.pfe.backend.dtos.chat.ChatResponse;
//import com.pfe.backend.security.JwtService;
//import com.pfe.backend.services.ChatbotService;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.Map;
//
//@Slf4j
//@RestController
//@RequestMapping("/api/chat")
//@RequiredArgsConstructor
//@CrossOrigin(origins = {
//        "http://localhost:5173",
//        "https://ton-domaine.com"
//})
//public class ChatController {
//
//    private final ChatbotService chatbotService;
//    private final JwtService jwtService;
//
//    @PostMapping("/ask")
//    public ResponseEntity<?> askChatbot(
//            @RequestBody Map<String, String> payload,
//            @RequestHeader(value = "Authorization", required = false) String authHeader
//    ) {
//        //  Validation
//        String question = payload.get("question");
//
//        if (question == null || question.isBlank()) {
//            return ResponseEntity.badRequest()
//                    .body("Question vide.");
//        }
//
//        // Extraction JWT (optionnel)
//        String username = extractUsername(authHeader);
//        log.info("Chatbot question: '{}' | user: {}", question,
//                username != null ? username : "visiteur");
//
//        //  Appel service
//        try {
//            ChatResponse response = chatbotService.ask(question, username);
//            return ResponseEntity.ok(response);
//
//        } catch (Exception e) {
//            log.error("Erreur chatbot : {}", e.getMessage());
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body("Service chatbot indisponible. Réessayez plus tard.");
//        }
//    }
//
//    // Méthode utilitaire
//    private String extractUsername(String authHeader) {
//        if (authHeader != null && authHeader.startsWith("Bearer ")) {
//            try {
//                return jwtService.extractUsername(authHeader.substring(7));
//            } catch (Exception e) {
//                log.warn("Token JWT invalide : {}", e.getMessage());
//            }
//        }
//        return null;  // visiteur anonyme
//    }
//}



//package com.pfe.backend.services;
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.JsonNode;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.pfe.backend.dtos.PageResponse;
//import com.pfe.backend.dtos.chat.ChatResponse;
//import com.pfe.backend.dtos.chat.SearchCriteria;
//import com.pfe.backend.dtos.evenement.EvenementResponseDto;
//import com.pfe.backend.entities.Categorie;
//import com.pfe.backend.entities.Client;
//import com.pfe.backend.exceptions.*;
//import com.pfe.backend.repositories.ClientRepository;
//import com.pfe.backend.services.implementations.EventServiceImpl;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.http.*;
//import org.springframework.stereotype.Service;
//import org.springframework.web.client.RestTemplate;
//
//import java.util.Map;

//import java.util.Objects;
//import java.util.stream.Collectors;
//
//@Slf4j
//@Service
//@RequiredArgsConstructor
//public class ChatbotService {
//
//    @Value("${ollama.url}")
//    private String ollamaUrl;
//
//    @Value("${ollama.model}")
//    private String model;
//
//    private final RestTemplate restTemplate;
//    private final ObjectMapper objectMapper;
//    private final ClientRepository clientRepository;
//    private final EventServiceImpl evenementService; // ton service existant
//
//    // ─── Point d'entrée principal ────────────────────────────────
//    public ChatResponse ask(String question, String username) throws Exception {
//
//        // ÉTAPE 1 : extraire les critères via LLM
//        SearchCriteria criteria = extractCriteria(question);
//        log.info("Critères extraits : {}", criteria);
//
//        // ÉTAPE 2 : enrichir avec le profil si connecté
//        boolean isConnected = false;
//        if (username != null) {
//            isConnected = enrichWithUserProfile(criteria, username);
//        }
//
//        // ÉTAPE 3 : recherche avec TON endpoint existant
//        PageResponse<EvenementResponseDto> results = evenementService.searchEvents(
//                0,                        // page
//                5,                        // max 5 résultats
//                criteria.getCategorieId(),
//                criteria.getKeyword(),
//                criteria.getVille(),
//                null,                     // date (non extrait pour l'instant)
//                criteria.getPrixMax()
//        );
//
//        // ÉTAPE 4 : construire la réponse naturelle
//        String message = buildMessage(criteria, results, isConnected);
//
//        return new ChatResponse(message, results.getContent(), criteria);
//    }
//
//    // ─── Extraction des critères via LLM ─────────────────────────
//    private SearchCriteria extractCriteria(String question) {
//        String prompt = """
//            Tu es un extracteur de critères de recherche d'événements.
//            Réponds UNIQUEMENT avec un objet JSON valide, sans texte avant ou après.
//
//            Champs disponibles :
//            - "ville" : ville marocaine mentionnée (Casablanca, Rabat, Marrakech, Fès, Tanger, Agadir, etc.) ou null
//            - "categorie" : parmi [Concert, Festival, Theatre, Sport, Conference, Art, Comedie, Cinema] ou null
//            - "prixMax" : nombre entier si un budget/prix max est mentionné ou null
//            - "keyword" : mot-clé du titre de l'événement ou null
//
//            Exemples :
//            Question: "concert pas cher à Casablanca"
//            Réponse: {"ville":"Casablanca","categorie":"Concert","prixMax":150,"keyword":null}
//
//            Question: "événements sportifs à Rabat ce week-end"
//            Réponse: {"ville":"Rabat","categorie":"Sport","prixMax":null,"keyword":null}
//
//            Question: "%s"
//            Réponse:
//            """.formatted(question);
//
//        try {
//            String raw = callOllama(prompt);
//            // Nettoyer la réponse (le LLM peut ajouter du texte autour)
//            String json = extractJson(raw);
//            SearchCriteria criteria = objectMapper.readValue(json, SearchCriteria.class);
//
//            // Mapper categorie → categorieId si besoin
//            criteria.setCategorieId(mapCategorieToId(criteria.getCategorie()));
//            return criteria;
//
//        } catch (Exception e) {
//            log.warn("Parsing critères échoué, retour critères vides : {}", e.getMessage());
//            return new SearchCriteria(); // recherche large
//        }
//    }
//
//    // ─── Enrichissement avec profil client ───────────────────────
//    private boolean enrichWithUserProfile(SearchCriteria criteria, String username) {
//        try {
//            Client client = clientRepository.findByUsername(username).orElse(null);
//            if (client == null) return false;
//
//            // Ville préférée (si non précisée dans la question)
//            if (criteria.getVille() == null && client.getReservations() != null) {
//                String villePreferee = client.getReservations().stream()
//                        .map(r -> r.getEvenement().getVille())
//                        .filter(Objects::nonNull)
//                        .collect(Collectors.groupingBy(v -> v, Collectors.counting()))
//                        .entrySet().stream()
//                        .max(Map.Entry.comparingByValue())
//                        .map(Map.Entry::getKey)
//                        .orElse(null);
//                criteria.setVille(villePreferee);
//                log.info("Ville enrichie depuis profil : {}", villePreferee);
//            }
//
//            // Catégorie préférée (si non précisée dans la question)
//            if (criteria.getCategorie() == null && client.getReservations() != null) {
//                Categorie catPreferee = client.getReservations().stream()
//                        .filter(r -> r.getEvenement() != null)
//                        .map(r -> r.getEvenement().getCategorie())
//                        .filter(Objects::nonNull)
//                        .collect(Collectors.groupingBy(c -> c, Collectors.counting()))
//                        .entrySet().stream()
//                        .max(Map.Entry.comparingByValue())
//                        .map(Map.Entry::getKey)
//                        .orElse(null);
//
//                if (catPreferee != null) {
//                    criteria.setCategorie(catPreferee.getNom());
//                    criteria.setCategorieId(mapCategorieToId(catPreferee.getNom()));
//                }
//            }
//
//            // Favoris : ajouter keyword depuis les titres favoris si rien d'autre
//            if (criteria.getKeyword() == null && client.getFavories() != null
//                    && !client.getFavories().isEmpty()) {
//                // on ne force pas le keyword depuis les favoris,
//                // mais on pourrait enrichir la recherche plus tard
//            }
//
//            return true;
//
//        } catch (Exception e) {
//            log.warn("Enrichissement profil échoué : {}", e.getMessage());
//            return false;
//        }
//    }
//
//    // ─── Construction du message naturel ─────────────────────────
//    private String buildMessage(SearchCriteria c,
//                                PageResponse<EvenementResponseDto> results,
//                                boolean isConnected) {
//        long total = results.getTotalElements();
//
//        if (total == 0) {
//            StringBuilder msg = new StringBuilder("Désolé, ");
//            msg.append("aucun événement trouvé");
//            if (c.getVille() != null)     msg.append(" à ").append(c.getVille());
//            if (c.getCategorie() != null) msg.append(" dans la catégorie ").append(c.getCategorie());
//            if (c.getPrixMax() != null)   msg.append(" sous ").append(c.getPrixMax()).append(" DH");
//            msg.append(". Essayez d'élargir vos critères 🔍");
//            return msg.toString();
//        }
//
//        StringBuilder msg = new StringBuilder();
//        msg.append("J'ai trouvé **").append(total).append(" événement")
//                .append(total > 1 ? "s**" : "**");
//
//        if (c.getVille() != null)     msg.append(" à **").append(c.getVille()).append("**");
//        if (c.getCategorie() != null) msg.append(" · catégorie **").append(c.getCategorie()).append("**");
//        if (c.getPrixMax() != null)   msg.append(" · max **").append(c.getPrixMax()).append(" DH**");
//
//        if (isConnected) {
//            msg.append(" _(recommandations basées sur votre profil)_");
//        }
//
//        msg.append(" ✨");
//        return msg.toString();
//    }
//
//    // ─── Appel Ollama ─────────────────────────────────────────────
//    private String callOllama(String prompt) throws JsonProcessingException {
//        Map<String, Object> body = Map.of(
//                "model", model,
//                "prompt", prompt,
//                "stream", false
//        );
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//        HttpEntity<String> entity = new HttpEntity<>(
//                objectMapper.writeValueAsString(body), headers
//        );
//        ResponseEntity<String> response = restTemplate.exchange(
//                ollamaUrl, HttpMethod.POST, entity, String.class
//        );
//        JsonNode root = objectMapper.readTree(response.getBody());
//        return root.path("response").asText("");
//    }
//
//    // ─── Extraire le JSON depuis la réponse brute du LLM ─────────
//    private String extractJson(String raw) {
//        // Le LLM peut répondre avec du texte avant/après le JSON
//        int start = raw.indexOf('{');
//        int end   = raw.lastIndexOf('}');
//        if (start != -1 && end != -1 && end > start) {
//            return raw.substring(start, end + 1);
//        }
//        return "{}"; // fallback
//    }
//
//    // ─── Mapper nom catégorie → ID ────────────────────────────────
//    private Long mapCategorieToId(String categorie) {
//        if (categorie == null) return null;
//        // Adapter selon tes vraies IDs en BDD
//        Map<String, Long> map = Map.of(
//                "Concert",    1L,
//                "Festival",   2L,
//                "Theatre",    3L,
//                "Sport",      4L,
//                "Conference", 5L,
//                "Art",        6L,
//                "Comedie",    7L,
//                "Cinema",     8L
//        );
//        return map.get(categorie);
//    }
//}