package com.ouadia.rovista1.dtos.organisateur;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class CreateUpdateEventRequest {
    
    @NotBlank
    String titre;
    String description;
    @NotNull
    LocalDateTime date;
    @NotBlank String lieu;
    @NotNull @PositiveOrZero
    Double prix;
    @NotNull @Positive
    Integer capacite;
    String categorie;
    String status;          // "Brouillon" | "Publié"
}