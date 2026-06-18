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
    LocalDateTime dateDebut;
    @NotNull
    LocalDateTime dateFin;
    @NotBlank String lieuSpecifique;
    @NotBlank String ville;
    @NotNull @PositiveOrZero
    Double prix;
    @NotNull @Positive
    Integer capacite;
    Integer nbPlacesVIP;
    Double prixVIP;
    Integer categorieId;
    String categorie;
    String statutEvenement;          // "Brouillon" | "Publié"
}