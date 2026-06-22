package com.pfe.backend.mappers;

import com.pfe.backend.dtos.admin.AdminCategorieDTO;
import com.pfe.backend.dtos.admin.AdminCategorieRequestDTO;
import com.pfe.backend.dtos.categorie.CategorieRequestDto;
import com.pfe.backend.dtos.categorie.CategorieResponseDto;
import com.pfe.backend.entities.Categorie;
import org.springframework.stereotype.Component;

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
