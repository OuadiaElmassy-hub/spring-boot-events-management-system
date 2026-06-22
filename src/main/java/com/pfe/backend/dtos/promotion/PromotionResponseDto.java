package com.pfe.backend.dtos.promotion;

import com.pfe.backend.entities.enums.TypePromotion;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class PromotionResponseDto {
    private Long id;
    private String titre;
    private LocalDate dateDebut;
    private LocalDate dateFin;
    private TypePromotion type;
    private boolean estApprove;
    private Long organisateurId;
    private List<Long> clientsId;
    private List<Long> evenementsId;
}
