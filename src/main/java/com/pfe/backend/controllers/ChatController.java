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
@CrossOrigin(origins = {
        "http://localhost:5173",
        "https://ton-domaine.com" // TODO: remplacer par le vrai domaine de prod avant déploiement
})
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