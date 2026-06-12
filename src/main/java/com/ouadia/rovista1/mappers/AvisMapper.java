package com.ouadia.rovista1.mappers;


import com.ouadia.rovista1.dtos.avis.AvisRequestDto;

import com.ouadia.rovista1.dtos.avis.AvisResponseDto;
import com.ouadia.rovista1.entities.Avis;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class AvisMapper {

    public  static Avis mappingAvisDtoRequestToAvis(AvisRequestDto dto){
        return Avis.builder()
                .comment(dto.getComment())
                .note(dto.getNote())
                .dateAvis(dto.getDateAvis())
                .build();
    }
    public static AvisResponseDto mappingAvisToAvisDtoResponse(Avis avis){
        return AvisResponseDto.builder()
                .id(avis.getId())
                .comment(avis.getComment())
                .note(avis.getNote())
                .dateAvis(avis.getDateAvis())
                .evenementId(avis.getEvenement() != null ? avis.getEvenement().getId() : null)
                .clientId(avis.getClient() != null ? avis.getClient().getId() : null)
                .clientNom(avis.getClient() != null ? avis.getClient().getNom() : null)
                .clientPrenom(avis.getClient() != null ? avis.getClient().getPrenom() : null)
                .visiteurId(avis.getVisiteur() != null ? avis.getVisiteur().getId() : null)
                .visiteurNom(avis.getVisiteur() != null ? avis.getVisiteur().getNom() : null)
                .build();
    }
}
