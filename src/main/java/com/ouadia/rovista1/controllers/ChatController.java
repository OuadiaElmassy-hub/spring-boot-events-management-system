package com.ouadia.rovista1.controllers;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.ouadia.rovista1.exceptions.BilletNotFoundException;
import com.ouadia.rovista1.exceptions.FavorieNotFoundException;
import com.ouadia.rovista1.exceptions.PromotionNotFoundException;
import com.ouadia.rovista1.exceptions.ReservationNotFoundException;
import com.ouadia.rovista1.services.ChatbotService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/chat")
@RequiredArgsConstructor
@CrossOrigin(origins = "*") // Permet les appels depuis l'extérieur (Mobile / Web)
public class ChatController {

    private final ChatbotService chatbotService;

    @PostMapping("/ask")
    public ResponseEntity<String> askChatbot(@RequestBody Map<String, String> payload) throws PromotionNotFoundException, ReservationNotFoundException, BilletNotFoundException, FavorieNotFoundException, JsonProcessingException {
        String question = payload.get("question");
        if (question == null || question.isBlank()) {
            return ResponseEntity.badRequest().body("La question ne peut pas être vide.");
        }

        String response = chatbotService.ask(question);
        return ResponseEntity.ok(response);
    }
}
