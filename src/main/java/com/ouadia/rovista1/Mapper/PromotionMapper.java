package com.ouadia.rovista1.Mapper;

import com.ouadia.rovista1.dtos.PromotionDto;
import com.ouadia.rovista1.entities.Promotion;

public class PromotionMapper {
    public static PromotionDto mapToPromotionDto(Promotion promotion){

        return new PromotionDto(
                promotion.getId(),
                promotion.getTitre(),
                promotion.getDateDebut(),
                promotion.getDateFin(),
                promotion.getType(),
                promotion.isEstApprove()
        );
    }

    public static Promotion mapToPromotion(PromotionDto dto){

        return new Promotion(
                dto.getId(),
                dto.getTitre(),
                dto.getDateDebut(),
                dto.getDateFin(),
                dto.getType(),
                dto.isEstApprove(),
                null,
                null,
                null
        );
    }
}
