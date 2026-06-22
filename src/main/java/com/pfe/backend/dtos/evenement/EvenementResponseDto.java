package com.pfe.backend.dtos.evenement;

import com.pfe.backend.entities.enums.StatutEvenement;
import lombok.*;

import java.time.LocalDateTime;
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
    private int nbPlacesVIP;
    private int placesVIPRestantes;
    private double prixVIP;
    private double prix; // pour une place normale
    private StatutEvenement statutEvenement;

    private List<String> imagesUrls;

    private Long categorieId;
    private Long organisateurId;

    private double rating;
}
