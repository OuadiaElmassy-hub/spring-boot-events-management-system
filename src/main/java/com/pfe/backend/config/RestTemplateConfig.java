package com.pfe.backend.config;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

/**
 * AJOUT — Cette classe n'existait pas avant.
 *
 * Problème corrigé : le RestTemplate utilisé pour appeler Ollama n'avait
 * aucun timeout. Si Ollama est lent, ne répond pas, ou est down,
 * le thread HTTP qui traite la requête /api/chat/ask reste bloqué
 * indéfiniment. Sous charge, ça épuise le pool de threads Tomcat
 * et bloque TOUTE l'application (pas seulement le chatbot).
 *
 * Avec ces timeouts :
 * - connectTimeout : si Ollama ne répond pas à la connexion en 5s -> échec
 * - readTimeout    : si Ollama met plus de 30s à répondre -> échec
 * (30s car la génération LLM peut être plus lente qu'un appel REST classique)
 *
 * Ajuste ces valeurs selon la taille de ton modèle / matériel.
 */
@Configuration
public class RestTemplateConfig {

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(5_000);   // 5 secondes pour établir la connexion
        factory.setReadTimeout(30_000);     // 30 secondes pour recevoir la réponse

        return builder
                .requestFactory(() -> factory)
                .build();
    }
}