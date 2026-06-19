package com.pfe.backend.dtos.evenement;

import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EvenementRequestDto {

    @NotBlank
    private String titre;
    @NotBlank
    private String description;
    @NotNull
    private LocalDateTime dateDebut;
    @NotNull
    private LocalDateTime dateFin;
    @NotNull
    private LocalTime heureDebut;
    @NotBlank
    private String lieuSpecifique;
    @NotBlank
    private String ville; //enum
    @Min(10)
    private int capacite;
    private int nbPlaceVIP = 0;
    private double prixVIP = 0;
    @Positive
    private double prix; // pour une place normale
    @Positive
    private Long categorieId;
}
