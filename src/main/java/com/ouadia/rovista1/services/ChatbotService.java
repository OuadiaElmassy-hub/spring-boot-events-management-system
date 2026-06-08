package com.ouadia.rovista1.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ouadia.rovista1.exceptions.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatbotService {

    @Value("${ollama.url}")
    private String ollamaUrl;

    @Value("${ollama.model}")
    private String model;
    private final RestTemplate restTemplate;
    private final RovistaAssistantService assistantService;
    private final ObjectMapper objectMapper; // inject as a bean

    public String ask(String question)
            throws PromotionNotFoundException, ReservationNotFoundException,
            FavorieNotFoundException, BilletNotFoundException, JsonProcessingException {
         /*  Authentication auth =
                SecurityContextHolder.getContext()
                        .getAuthentication();

        Client client =
                clientRepository.findByUsername(auth.getName())
                        .orElseThrow("Veuillez vous connecter à votre compte");
        String q = question.toLowerCase();
        if (q.contains("réservation")
                || q.contains("حجز")
                || q.contains("حجوزات")) {

            return assistantService.getClientReservations(client);
        }

        if (q.contains("billet")
                || q.contains("ticket")
                || q.contains("تذكرة")
                || q.contains("تذاكر")) {

            return assistantService.getClientBillets(client);
        }

        if (q.contains("favori")
                || q.contains("مفضل")
                || q.contains("المفضلة")) {

            return assistantService.getClientFavoris(client);
        }

        if (q.contains("profil")
                || q.contains("compte")
                || q.contains("حسابي")
                || q.contains("ملفي")) {

            return assistantService.getClientProfile(client);
        }

        if (q.contains("promotion")
                || q.contains("promotions")
                || q.contains("réduction")
                || q.contains("offre")
                || q.contains("عروض")
                || q.contains("تخفيضات")) {

            return assistantService.getClientPromotions(client);
        }

        String context =
                assistantService.getApplicationContext()
                        + "\n"
                        + assistantService.getClientContext(client);

       */

        String context = assistantService.getApplicationContext();

        String prompt = """
                Tu es l'assistant de Rovista.
                Contexte : %s
                Question : %s
                Réponds de façon concise.
                """.formatted(context, question);

        Map<String, Object> body = Map.of(
                "model", model,
                "prompt", prompt,
                "stream", false
        );


        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity =
                new HttpEntity<>(objectMapper.writeValueAsString(body), headers);

        ResponseEntity<String> response =
                restTemplate.exchange(ollamaUrl, HttpMethod.POST, entity, String.class);

        JsonNode root = objectMapper.readTree(response.getBody());
        return root.path("response").asText("Aucune réponse.");
    }
}