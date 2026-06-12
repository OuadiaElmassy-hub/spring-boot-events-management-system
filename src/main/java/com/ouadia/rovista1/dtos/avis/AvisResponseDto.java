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
    private String clientNom;
    private String avatarUrl;
    private String visiteurNom;
    private Long evenementId;
<<<<<<< HEAD

=======
    private Long clientId;
    private String clientNom;      // ← ajoute
    private String clientPrenom;   // ← ajoute
    private Long visiteurId;
    private String visiteurNom;    // ← ajoute
>>>>>>> 1e894bbb80d513546d55e1a91703946d40521ea3
}
