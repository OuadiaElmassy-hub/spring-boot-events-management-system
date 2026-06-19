package com.pfe.backend.dtos.evenement;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateEvenementRequestDto {

    @NotBlank
    private String titre;
    @NotBlank
    private String description;
    @NotNull
    private LocalDateTime dateDebut;
    @NotNull
    private LocalDateTime dateFin;
    @NotBlank
    private String lieuSpecifique;
    @NotBlank
    private String ville; //enum
    @Min(10)
    private int capacite;
    @Min(10)
    private int placesRestant;
    @Positive
    private double prix; // pour une place normale
    @Positive
    private Long categorieId;
    @Positive
    private Long promotionId;
}
