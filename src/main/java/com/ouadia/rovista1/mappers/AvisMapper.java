package com.ouadia.rovista1.mappers;


import com.ouadia.rovista1.dtos.avis.AvisRequestDto;

import com.ouadia.rovista1.dtos.avis.AvisResponseDto;
import com.ouadia.rovista1.entities.Avis;


public class AvisMapper {

    public  static Avis mappingAvisDtoRequestToAvis(AvisRequestDto dto){
        return Avis.builder()
                .comment(dto.getComment())
                .note(dto.getNote())
                .dateAvis(dto.getDateAvis())
                .build();
    }
    public static AvisResponseDto mappingAvisToAvisDtoResponse(Avis e){
        return AvisResponseDto.builder()
                .comment(e.getComment())
                .note(e.getNote())
                .dateAvis(e.getDateAvis())
                .evenementId(e.getEvenement().getId())
                .clientId(e.getClient().getId())
                .visiteurId(e.getVisiteur().getId())
                .build();
    }
}
