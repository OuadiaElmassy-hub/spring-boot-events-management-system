package com.pfe.backend.exceptions;

import org.springframework.web.client.ResourceAccessException;

public class OllamaUnavailableException extends Exception {
    public OllamaUnavailableException(String message) {
        super(message);
    }
}
