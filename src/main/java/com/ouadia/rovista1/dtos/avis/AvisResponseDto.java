package com.ouadia.rovista1.dtos.avis;

import com.ouadia.rovista1.entities.Client;
import com.ouadia.rovista1.entities.Evenement;
import com.ouadia.rovista1.entities.VisiteurInvite;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data // pour les methode getter, setter, toString() , hachcode() ,equals()
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class AvisResponseDto {
    private Long id;
    private String comment;
    private double note;
    private LocalDate dateAvis;
    private Long evenementId;
    private Long clientId;
    private String clientNom;      // ← ajoute
    private String clientPrenom;   // ← ajoute
    private Long visiteurId;
    private String visiteurNom;    // ← ajoute
}
