package com.pfe.backend.mappers;


import com.pfe.backend.dtos.avis.AvisRequestDto;

import com.pfe.backend.dtos.avis.AvisResponseDto;
import com.pfe.backend.entities.Avis;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;

@Component
@AllArgsConstructor
public class AvisMapper {

    public  Avis mappingAvisDtoRequestToAvis(AvisRequestDto dto){
        return Avis.builder()
                .comment(dto.getComment())
                .note(dto.getNote())
                .dateAvis(LocalDateTime.now())
                .build();
    }

    public AvisResponseDto mappingAvisToAvisDtoResponse(Avis avis){
        return AvisResponseDto.builder()
                .id(avis.getId())
                .comment(avis.getComment())
                .note(avis.getNote())
                .nom(resolveNom(avis))
                .prenom(resolveNom(avis))
                .dateAvis(avis.getDateAvis())
                .evenementId(avis.getEvenement() != null ? avis.getEvenement().getId() : null)
                .build();
    }

    private String resolveNom(Avis avis) {
        if (avis.getClient() != null) {
            return avis.getClient().getNom();
        } else if (avis.getVisiteur() != null) {
            return avis.getVisiteur().getNom();
        }
        return null;
    }

}
