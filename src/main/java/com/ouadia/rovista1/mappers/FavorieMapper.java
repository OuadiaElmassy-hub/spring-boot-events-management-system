package com.ouadia.rovista1.mappers;

import com.ouadia.rovista1.dtos.favorie.FavorieRequestDto;
import com.ouadia.rovista1.dtos.favorie.FavorieResponseDto;
import com.ouadia.rovista1.entities.Client;
import com.ouadia.rovista1.entities.Evenement;
import com.ouadia.rovista1.entities.Favorie;

import com.ouadia.rovista1.entities.Promotion;
import com.ouadia.rovista1.services.implementations.ClientServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor

public class FavorieMapper {
  ClientServiceImpl service;

    public  Favorie mappingFavorieDtoRequestToFavorie(FavorieRequestDto dto){
        return Favorie.builder()
                .description(dto.getDescription())
                .dateCreation(dto.getDateCreation())
                .client(service.getClientEntityById(dto.getClientId()))
                .build();
    }
    public static FavorieResponseDto mappingFavorieToFavorieDtoResponse(Favorie e){
        return FavorieResponseDto.builder()
                .description(e.getDescription())
                .dateCreation(e.getDateCreation())
                .clientId(e.getClient().getId())
                .evenementsId(e.getEvenements().stream().map(Evenement::getId).toList())
                .build();
    }
}
