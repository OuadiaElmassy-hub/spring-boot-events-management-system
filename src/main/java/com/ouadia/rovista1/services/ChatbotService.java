package com.ouadia.rovista1.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pfe.backend.dtos.PageResponse;
import com.pfe.backend.dtos.chat.ChatResponse;
import com.pfe.backend.dtos.chat.SearchCriteria;
import com.pfe.backend.dtos.evenement.EvenementResponseDto;
import com.pfe.backend.entities.Categorie;
import com.pfe.backend.entities.Client;
import com.pfe.backend.repositories.ClientRepository;
import com.pfe.backend.services.implementations.EventServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatbotService {

    @Value("${ollama.url}")
    private String ollamaUrl;

    @Value("${ollama.model}")
    private String model;

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private final ClientRepository clientRepository;
    private final EventServiceImpl evenementService;

    public ChatResponse ask(String question, String username) throws Exception {
        SearchCriteria criteria = extractCriteria(question);
        log.info("Ville = {}", criteria.getVille());
        log.info("Categorie = {}", criteria.getCategorie());
        log.info("PrixMax = {}", criteria.getPrixMax());
        log.info("Keyword = {}", criteria.getKeyword());
        log.info("Critères extraits : {}", criteria);

        boolean hasExplicitCriteria =
                criteria.getVille() != null
                        || criteria.getCategorie() != null
                        || criteria.getPrixMax() != null
                        || criteria.getKeyword() != null;

        boolean isConnected = false;
        if (username != null && hasExplicitCriteria) {
            isConnected = enrichWithUserProfile(criteria, username);
            log.info("Après enrichissement : {}", criteria);
        }

        PageResponse<EvenementResponseDto> results = evenementService.searchEvents(
                0, 5,
                criteria.getCategorieId(),
                criteria.getKeyword(),
                criteria.getVille(),
                null,
                criteria.getPrixMax()
        );

        String message = buildMessage(criteria, results, isConnected);
        return ChatResponse.builder()
                .message(message)
                .events(results.getContent()).criteresUtilises(criteria)
                .total(results.getTotalElements()).build();
    }
    private SearchCriteria extractCriteria(String question) {
        String prompt = """
            Tu es un extracteur de critères strict pour un système d'événements au Maroc.
            Examine le message de l'utilisateur et extrait les critères.
            ⚠️ RÈGLE CRITIQUE : Ne devine JAMAIS et n'invente JAMAIS une ville ou un mot-clé si ce n'est pas écrit textuellement. Si un champ n'est pas mentionné, mets obligatoirement null.

            Champs disponibles :
            - "ville" : Uniquement si une ville marocaine est explicitement écrite (Casablanca, Rabat, Marrakech, Fès, Tanger, Agadir, etc.), sinon null.
            - "categorie" : Uniquement parmi ces choix exacts [Concert, Festival, Theatre, Sport, Conference, Art, Comedie, Cinema], sinon null.
            - "prixMax" : Un nombre entier pour le budget maximum, sinon null.
            - "keyword" : Le nom propre de l'événement s'il est mentionné (ex: Mawazine, Jazzablanca), sinon null.

            Exemples de comportement attendu :
            Question: "concert à Casablanca moins de 150dh"
            Réponse: {"ville":"Casablanca","categorie":"Concert","prixMax":150,"keyword":null}

            Question: "mawazine festival"
            Réponse: {"ville":null,"categorie":"Festival","prixMax":null,"keyword":"Mawazine"}

            Question: "théâtre à Rabat"
            Réponse: {"ville":"Rabat","categorie":"Theatre","prixMax":null,"keyword":null}

            Question: "%s"
            Réponse STRICTEMENT au format JSON valide :
            """.formatted(question);

        try {
            String raw = callOllama(prompt);
            log.info("Réponse brute Ollama : {}", raw);
            String json = extractJson(raw);
            SearchCriteria criteria = objectMapper.readValue(json, SearchCriteria.class);


            if (criteria.getCategorie() != null) {
                criteria.setCategorieId(mapCategorieToId(criteria.getCategorie().trim()));
            }
            if (criteria.getVille() != null) {
                criteria.setVille(normalizeVille(criteria.getVille().trim()));
            }
            return criteria;

        } catch (Exception e) {
            log.warn("Parsing critères échoué, retour critères vides : {}", e.getMessage());
            return new SearchCriteria();
        }
    }

    private String normalizeVille(String ville) {
        if (ville == null) return null;

        Map<String, String> villesMap = Map.of(
                "casablanca", "Casablanca",
                "casablanc",  "Casablanca",
                "casa",       "Casablanca",
                "rabat",      "Rabat",
                "marrakech",  "Marrakech",
                "marrakesh",  "Marrakech",
                "fes",        "Fès",
                "fès",        "Fès",
                "tanger",     "Tanger",
                "agadir",     "Agadir"
        );

        return villesMap.getOrDefault(ville.toLowerCase().trim(), ville);
    }

    private boolean enrichWithUserProfile(SearchCriteria criteria, String username) {
        try {
            Client client = clientRepository.findByUsername(username).orElse(null);
            if (client == null) return false;

            if (criteria.getVille() == null && client.getReservations() != null) {
                String villePreferee = client.getReservations().stream()
                        .map(r -> r.getEvenement().getVille())
                        .filter(Objects::nonNull)
                        .collect(Collectors.groupingBy(v -> v, Collectors.counting()))
                        .entrySet().stream()
                        .max(Map.Entry.comparingByValue())
                        .map(Map.Entry::getKey)
                        .orElse(null);
                criteria.setVille(villePreferee);
                log.info("Ville enrichie depuis profil : {}", villePreferee);
            }

            if (criteria.getCategorie() == null && client.getReservations() != null) {
                Categorie catPreferee = client.getReservations().stream()
                        .filter(r -> r.getEvenement() != null)
                        .map(r -> r.getEvenement().getCategorie())
                        .filter(Objects::nonNull)
                        .collect(Collectors.groupingBy(c -> c, Collectors.counting()))
                        .entrySet().stream()
                        .max(Map.Entry.comparingByValue())
                        .map(Map.Entry::getKey)
                        .orElse(null);

                if (catPreferee != null) {
                    criteria.setCategorie(catPreferee.getNom());
                    criteria.setCategorieId(mapCategorieToId(catPreferee.getNom()));
                }
            }

            return true;
        } catch (Exception e) {
            log.warn("Enrichissement profil échoué : {}", e.getMessage());
            return false;
        }
    }

    private String buildMessage(SearchCriteria c, PageResponse<EvenementResponseDto> results, boolean isConnected) {
        long total = results.getTotalElements();

        if (total == 0) {
            StringBuilder msg = new StringBuilder("Désolé, aucun événement trouvé");
            if (c.getVille() != null)     msg.append(" à ").append(c.getVille());
            if (c.getCategorie() != null) msg.append(" dans la catégorie ").append(c.getCategorie());
            if (c.getPrixMax() != null)   msg.append(" sous ").append(c.getPrixMax()).append(" DH");
            if (c.getKeyword() != null)   msg.append(" avec le mot-clé \"").append(c.getKeyword()).append("\"");
            msg.append(". Essayez d'élargir vos critères 🔍");
            return msg.toString();
        }

        StringBuilder msg = new StringBuilder("J'ai trouvé ").append(total)
                .append(" événement").append(total > 1 ? "s" : "");

        if (c.getVille() != null)     msg.append(" à ").append(c.getVille());
        if (c.getCategorie() != null) msg.append(" · catégorie ").append(c.getCategorie());
        if (c.getPrixMax() != null)   msg.append(" · max ").append(c.getPrixMax()).append(" DH");
        if (c.getKeyword() != null)   msg.append(" · pour \"").append(c.getKeyword()).append("\"");

        if (isConnected) msg.append(" (recommandations basées sur votre profil)");
        msg.append(" ✨");
        return msg.toString();
    }

    private String callOllama(String prompt) throws JsonProcessingException {
        Map<String, Object> options = Map.of("temperature", 0.0);

        Map<String, Object> body = Map.of(
                "model", model,
                "prompt", prompt,
                "stream", false,
                "options", options
        );

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(
                objectMapper.writeValueAsString(body), headers
        );
        ResponseEntity<String> response = restTemplate.exchange(
                ollamaUrl, HttpMethod.POST, entity, String.class
        );
        JsonNode root = objectMapper.readTree(response.getBody());
        return root.path("response").asText("");
    }

    private String extractJson(String raw) {
        int start = raw.indexOf('{');
        int end   = raw.lastIndexOf('}');
        if (start != -1 && end != -1 && end > start) {
            return raw.substring(start, end + 1);
        }
        return "{}";
    }

    private Long mapCategorieToId(String categorie) {
        if (categorie == null) return null;


        Map<String, Long> map = new HashMap<>();
        map.put("concert",    1L);
        map.put("festival",   2L);
        map.put("theatre",    3L);
        map.put("sport",      4L);
        map.put("conference", 5L);
        map.put("art",        6L);
        map.put("comedie",    7L);
        map.put("cinema",     8L);

        return map.get(categorie.toLowerCase().trim());
    }
}