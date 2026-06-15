package com.ouadia.rovista1.mappers;

import com.ouadia.rovista1.dtos.admin.AdminCategorieDTO;
import com.ouadia.rovista1.dtos.admin.AdminCategorieRequestDTO;
import com.ouadia.rovista1.dtos.categorie.CategorieRequestDto;
import com.ouadia.rovista1.dtos.categorie.CategorieResponseDto;
import com.ouadia.rovista1.entities.Categorie;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class CategorieMapper {

    public CategorieResponseDto mapToDto(Categorie categorie){

        return CategorieResponseDto.builder()
                .id(categorie.getId())
                .nom(categorie.getNom())
                .description(categorie.getDescription())
                .iconUrl(categorie.getIconUrl())
                .build();
    }

    public Categorie mapToCategorie(CategorieRequestDto categorieDto){

        return Categorie.builder()
                .nom(categorieDto.getNom())
                .description(categorieDto.getDescription())
                .build();
    }

    public Categorie mapToCategorie(AdminCategorieRequestDTO categorieDto){

        return Categorie.builder()
                .nom(categorieDto.getNom())
                .description(categorieDto.getDescription())
                .couleur(categorieDto.getCouleur())
                .build();
    }

    public AdminCategorieDTO mapToDtoAdmin(Categorie categorie){

        return AdminCategorieDTO.builder()
                .id(categorie.getId())
                .nom(categorie.getNom())
                .description(categorie.getDescription())
                .iconUrl(categorie.getIconUrl())
                .couleur(categorie.getCouleur())
                .active(categorie.getActive())
                .createdAt(categorie.getCreatedAt())
                .build();
    }
}
