package com.pfe.backend.exceptions;

/**
 * AJOUT — Exception dédiée.
 *
 * Avant, toute erreur (réseau, parsing, NPE...) remontait comme une
 * Exception générique, et le contrôleur renvoyait systématiquement
 * un 500 avec le même message, sans distinction.
 *
 * Cette exception permet au contrôleur de répondre 503 (Service
 * Unavailable) spécifiquement quand Ollama est injoignable/lent,
 * ce qui est sémantiquement plus correct et plus facile à monitorer
 * côté frontend (un 503 peut déclencher un message "réessayez" différent
 * d'un 500 qui signale un vrai bug).
 */
public class OllamaUnavailableException extends RuntimeException {

    public OllamaUnavailableException(String message, Throwable cause) {
        super(message, cause);
    }
}