package com.pfe.backend.mappers;

import com.pfe.backend.dtos.favorie.FavorieRequestDto;
import com.pfe.backend.dtos.favorie.FavorieResponseDto;
import com.pfe.backend.entities.Favorie;

import com.pfe.backend.services.client.ClientServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class FavorieMapper {
  ClientServiceImpl service;

    public Favorie mappingFavorieDtoRequestToFavorie(FavorieRequestDto dto){
        return Favorie.builder()
                .description(dto.getDescription())
                .dateCreation(dto.getDateCreation())
                .client(service.getClientEntityById(dto.getClientId()))
                .build();
    }
    public FavorieResponseDto mappingFavorieToFavorieDtoResponse(Favorie e){
        return FavorieResponseDto.builder()
                .description(e.getDescription())
                .dateCreation(e.getDateCreation())
                .clientId(e.getClient().getId())
                .evenementId(e.getEvenement().getId())
                .build();
    }
}
