package com.ouadia.rovista1.controllers;

import com.ouadia.rovista1.dtos.chat.ChatResponse;
import com.ouadia.rovista1.security.JwtService;
import com.ouadia.rovista1.services.ChatbotService;
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
        "https://ton-domaine.com"
})
public class ChatController {

    private final ChatbotService chatbotService;
    private final JwtService jwtService;

    @PostMapping("/ask")
    public ResponseEntity<?> askChatbot(
            @RequestBody Map<String, String> payload,
            @RequestHeader(value = "Authorization", required = false) String authHeader
    ) {
        //  Validation
        String question = payload.get("question");

        if (question == null || question.isBlank()) {
            return ResponseEntity.badRequest()
                    .body("Question vide.");
        }

        // Extraction JWT (optionnel)
        String username = extractUsername(authHeader);
        log.info("Chatbot question: '{}' | user: {}", question,
                username != null ? username : "visiteur");

        //  Appel service
        try {
            ChatResponse response = chatbotService.ask(question, username);
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            log.error("Erreur chatbot : {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Service chatbot indisponible. Réessayez plus tard.");
        }
    }

    // Méthode utilitaire
    private String extractUsername(String authHeader) {
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            try {
                return jwtService.extractUsername(authHeader.substring(7));
            } catch (Exception e) {
                log.warn("Token JWT invalide : {}", e.getMessage());
            }
        }
        return null;  // visiteur anonyme
    }
}