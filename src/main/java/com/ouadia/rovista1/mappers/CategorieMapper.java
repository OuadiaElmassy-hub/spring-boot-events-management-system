package com.ouadia.rovista1.mappers;

import com.ouadia.rovista1.dtos.categorie.CategorieRequestDto;
import com.ouadia.rovista1.dtos.categorie.CategorieResponseDto;
import com.ouadia.rovista1.entities.Categorie;
import org.springframework.stereotype.Component;

@Component
public class CategorieMapper {

    public CategorieResponseDto mapToDto(Categorie categorie){

        return CategorieResponseDto.builder()
                .id(categorie.getId())
                .nom(categorie.getNom())
                .description(categorie.getDescription())
                .imageUrl(categorie.getImage() != null ? categorie.getImage().getUrl() : null)
                .build();
    }

    public Categorie mapToCategorie(CategorieRequestDto categorieDto){

        return Categorie.builder()
                .nom(categorieDto.getNom())
                .description(categorieDto.getDescription())
                .build();
    }
}
