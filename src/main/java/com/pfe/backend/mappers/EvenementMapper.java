package com.pfe.backend.mappers;

import com.pfe.backend.dtos.evenement.EvenementRequestDto;
import com.pfe.backend.dtos.evenement.EvenementResponseDto;
import com.pfe.backend.entities.Avis;
import com.pfe.backend.entities.Evenement;
import com.pfe.backend.services.implementations.ImageServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;


@AllArgsConstructor
@Component
public class EvenementMapper {

    private ImageServiceImpl imageService;

    public Evenement mappingEvenementDtoRequestToEvenement(EvenementRequestDto dto){
        return Evenement.builder()
                .titre(dto.getTitre())
                .description(dto.getDescription())
                .lieuSpecifique(dto.getLieuSpecifique())
                .ville(dto.getVille())
                .dateDebut(dto.getDateDebut())
                .dateFin(dto.getDateFin())
                .capacite(dto.getCapacite())
                .nbPlacesVIP(dto.getNbPlaceVIP())      // ← ajoute
                .placesVIPRestantes(dto.getNbPlaceVIP()) // ← ajoute (au début = nbPlaceVIP)
                .prixVIP(dto.getPrixVIP())
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
                .id(e.getId())
                .titre(e.getTitre())
                .description(e.getDescription())
                .lieuSpecifique(e.getLieuSpecifique())
                .ville(e.getVille())
                .dateDebut(e.getDateDebut())
                .dateFin(e.getDateFin())
                .capacite(e.getCapacite())
                .placesRestants(e.getPlacesRestants())
                .nbPlacesVIP(e.getNbPlacesVIP())
                .placesVIPRestantes(e.getPlacesVIPRestantes())
                .prixVIP(e.getPrixVIP())
                .prix(e.getPrix())
                .categorieId(e.getCategorie().getId())
                .organisateurId(e.getOrganisateur().getId())
                .imagesUrls(imageService.getAllImagesUrls(e.getImages()))
                .statutEvenement(e.getStatutEvenement())
                .rating(rating)
                .build();
    }
}
