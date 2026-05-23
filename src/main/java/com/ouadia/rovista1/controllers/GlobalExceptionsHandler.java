package com.ouadia.rovista1.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ouadia.rovista1.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionsHandler {

//    @ExceptionHandler(EventNotFoundException.class)
//    public ResponseEntity<Map<String, Object>> handleEventNotFoundMeta(EventNotFoundException ex) {
//        Map<String, Object> error = new HashMap<>();
//        error.put("error", ex.getMessage());
//        error.put("status", HttpStatus.NOT_FOUND.value());
//        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
//    }
    @ExceptionHandler(EventNotFoundException.class)
    public ResponseEntity<?> handleEventNotFound(EventNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }
    @ExceptionHandler(OrganisateurNotFoundException.class)
    public ResponseEntity<?> handleOrganisateurNotFound(OrganisateurNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(CategorieNotFoundException.class)
    public ResponseEntity<?> handleCategorieNotFound(CategorieNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<?> handleBusinessException(BusinessException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }
    @ExceptionHandler(StorageProblemException.class)
    public ResponseEntity<?> handelStorageProblem(StorageProblemException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> validation(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors()
                        .forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(JsonProcessingException.class)
    public ResponseEntity<?> handelJsonProcessing(JsonProcessingException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }
}
