package com.ouadia.rovista1.mappers;


import com.ouadia.rovista1.dtos.avis.AvisRequestDto;

import com.ouadia.rovista1.dtos.avis.AvisResponseDto;
import com.ouadia.rovista1.entities.Avis;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class AvisMapper {

    public  Avis mappingAvisDtoRequestToAvis(AvisRequestDto dto){
        return Avis.builder()
                .comment(dto.getComment())
                .note(dto.getNote())
                .dateAvis(LocalDateTime.now())
                .build();
    }
    public AvisResponseDto mappingAvisToAvisDtoResponse(Avis e){
        return AvisResponseDto.builder()
                .comment(e.getComment())
                .note(e.getNote())
                .dateAvis(e.getDateAvis())
                .clientNom(e.getClient().getNom())
                .evenementId(e.getEvenement().getId())
                .visiteurNom(e.getVisiteur().getNom())
                .build();
    }
}
