package com.ouadia.rovista1.Mapper;

import com.ouadia.rovista1.dtos.EvenementDto;
import com.ouadia.rovista1.entities.Evenement;

public class EventMapper {
    public static EvenementDto mapToEvenementDto(Evenement evenement){

        return new EvenementDto(
                evenement.getId(),
                evenement.getTitre(),
                evenement.getDescription(),
                evenement.getDateDebut(),
                evenement.getDateFin(),
                evenement.getHeureDebut(),
                evenement.getLieuSpecifique(),
                evenement.getVille(),
                evenement.getCapacite(),
                evenement.getPrix(),
                evenement.getStatutEvenement(),
                evenement.getFichierUri(),
                evenement.getImageUri()
        );
    }

    public static Evenement mapToEvenement(EvenementDto evenementDto){

        return new Evenement(
                evenementDto.getId(),
                evenementDto.getTitre(),
                evenementDto.getDescription(),
                evenementDto.getDateDebut(),
                evenementDto.getDateFin(),
                evenementDto.getHeureDebut(),
                evenementDto.getLieuSpecifique(),
                evenementDto.getVille(),
                evenementDto.getCapacite(),
                evenementDto.getPrix(),
                evenementDto.getStatutEvenement(),
                evenementDto.getFichierUri(),
                evenementDto.getImageUri(),
                null,
                null,
                null,
                null,
                null,
                null,
                null
        );
    }
}
