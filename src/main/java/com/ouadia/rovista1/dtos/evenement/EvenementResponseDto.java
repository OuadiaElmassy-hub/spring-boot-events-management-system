package com.ouadia.rovista1.dtos.evenement;

import com.ouadia.rovista1.entities.enums.StatutEvenement;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EvenementResponseDto {

    private Long id;
    private String titre;
    private String description;

    private LocalDateTime dateDebut;
    private LocalDateTime dateFin;

    // on peut avoir l'heure de debut apartir de dateDebut dans le front

    private String lieuSpecifique;
    private String ville; //enum

    private int capacite;
    private int placesRestants;

    private double prix; // pour une place normale
    private StatutEvenement statutEvenement;

    private List<String> imagesUrls;

    private Long categorieId;
    private Long organisateurId;

    private int nbPlacesVIP;

    private double rating;
}
