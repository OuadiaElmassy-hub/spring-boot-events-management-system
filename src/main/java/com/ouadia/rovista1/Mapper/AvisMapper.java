package com.ouadia.rovista1.Mapper;

import com.ouadia.rovista1.dtos.AvisDto;
import com.ouadia.rovista1.entities.Avis;

public class AvisMapper {
    public static AvisDto mapToAvisDto(Avis avis){
        return new AvisDto(
                avis.getId(),
                avis.getComment(),
                avis.getNote(),
                avis.getDateAvis()
        );
    }
    public static Avis mapToAvis(AvisDto avisDto){
        return new Avis(
                avisDto.getId(),
                avisDto.getComment(),
                avisDto.getNote(),
                avisDto.getDateAvis(),
                null,
                null,
                null
                );
    }
}
