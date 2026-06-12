package com.ouadia.rovista1.dtos.avis;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AvisResponseDto {

    private Long id;
    private Double note;
    private String comment;
    private LocalDateTime dateAvis;

    // Données anonymisées/publiques du client
    private String nom;
    private String prenom;
    private String avatarUrl;
    private Long evenementId;

    // pas besoin de id car on a recuperer le nom et prenom et avatar.
//    private Long clientId;
//    private Long visiteurId;

}
