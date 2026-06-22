package com.pfe.backend.dtos.promotion;

import com.pfe.backend.entities.enums.TypePromotion;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@Builder
public class PromotionRequestDto {
    private String titre;
    private LocalDate dateDebut;
    private LocalDate dateFin;
    private TypePromotion type;
    private boolean estApprove;
    private Long organisateurId;
}
