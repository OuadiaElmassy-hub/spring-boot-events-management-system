package com.ouadia.rovista1.dtos.organisateur;

import com.ouadia.rovista1.entities.enums.TypePromotion;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
@Getter
@Setter
@Builder
public class CreateUpdatePromotionRequest {

    private String code;
    private String titre;
    private String description;
    private TypePromotion type; // "POURCENTAGE";
    private BigDecimal valeur;
    private Long eventId;
    private LocalDate dateDebut;
    private LocalDate dateFin;
    private int maxUtilisations;
    private BigDecimal montantMinimum;

}
