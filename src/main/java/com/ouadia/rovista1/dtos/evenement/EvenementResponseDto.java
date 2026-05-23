package com.ouadia.rovista1.dtos.evenement;

import com.ouadia.rovista1.entities.enums.StatutEvenement;
import lombok.*;

import java.time.LocalDate;
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
    private LocalDate dateDebut;
    private LocalDate dateFin;
    private LocalTime heureDebut;
    private String lieuSpecifique;
    private String ville; //enum
    private int capacite;
    private double prix; // pour une place normale
    private StatutEvenement statutEvenement;
    private List<String> imagesUrls;
    private String categorie;
    private String organisateur;
    private double rating;
}
