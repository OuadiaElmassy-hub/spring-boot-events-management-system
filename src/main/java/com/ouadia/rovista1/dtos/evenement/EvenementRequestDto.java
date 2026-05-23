package com.ouadia.rovista1.dtos.evenement;

import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;
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
    private LocalDate dateDebut;
    @NotNull
    private LocalDate dateFin;
    @NotNull
    private LocalTime heureDebut;
    @NotBlank
    private String lieuSpecifique;
    @NotBlank
    private String ville; //enum
    @NotBlank
    @Min(10)
    private int capacite;
    @NotBlank
    @Positive
    private double prix; // pour une place normale
    @NotBlank
    @Positive
    private Long categorieId;
}
