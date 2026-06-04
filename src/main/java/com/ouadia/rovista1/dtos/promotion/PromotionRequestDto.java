package com.ouadia.rovista1.dtos.promotion;

import com.ouadia.rovista1.entities.enums.TypePromotion;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
@Data
@AllArgsConstructor
@Builder
public class PromotionRequestDto {
    private String titre;
    private LocalDateTime dateDebut;
    private LocalDateTime dateFin;
    private TypePromotion type;
    private boolean estApprove;
    private Long organisateurId;
}
