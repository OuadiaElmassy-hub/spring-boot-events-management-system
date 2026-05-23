package com.ouadia.rovista1.mappers;

import com.ouadia.rovista1.dtos.evenement.EvenementRequestDto;
import com.ouadia.rovista1.dtos.evenement.EvenementResponseDto;
import com.ouadia.rovista1.entities.Avis;
import com.ouadia.rovista1.entities.Evenement;
import com.ouadia.rovista1.services.implementations.ImageServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;


@AllArgsConstructor
@Component
public class EvenementMapper {

    private ImageServiceImpl imageService;

    public Evenement mappingEvenementDtoRequestToEvenement(EvenementRequestDto dto){
        new Evenement();
        return Evenement.builder()
                .titre(dto.getTitre())
                .description(dto.getDescription())
                .lieuSpecifique(dto.getLieuSpecifique())
                .ville(dto.getVille())
                .dateDebut(dto.getDateDebut())
                .dateFin(dto.getDateFin())
                .heureDebut(dto.getHeureDebut())
                .capacite(dto.getCapacite())
                .prix(dto.getPrix())
                .build();
    }
    public EvenementResponseDto mappingEvenementToEvenementDtoResponse(Evenement e){

        double rating = 0;
        if (e.getAvis() != null) {

            for (Avis a : e.getAvis()) {
                rating += a.getNote();
            }
            rating = rating / e.getAvis().size();
        }

        return EvenementResponseDto.builder()
                .titre(e.getTitre())
                .description(e.getDescription())
                .lieuSpecifique(e.getLieuSpecifique())
                .ville(e.getVille())
                .dateDebut(e.getDateDebut())
                .dateFin(e.getDateFin())
                .heureDebut(e.getHeureDebut())
                .capacite(e.getCapacite())
                .prix(e.getPrix())
                .categorie(e.getCategorie().getNom())
                .organisateur(e.getOrganisateur().getNomOrganisation())
                .imagesUrls(imageService.getAllImagesUrls(e.getImages()))
                .statutEvenement(e.getStatutEvenement())
                .rating(rating)
                .build();
    }
}
