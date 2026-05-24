package com.ouadia.rovista1.Mapper;

import com.ouadia.rovista1.dtos.CategorieDto;
import com.ouadia.rovista1.entities.Categorie;

public class CategorieMapper {
    public static CategorieDto mapToCategorieDto(Categorie categorie){

        return new CategorieDto(
                categorie.getId(),
                categorie.getNom(),
                categorie.getDescription()
        );
    }

    public static Categorie mapToCategorie(CategorieDto categorieDto){

        return new Categorie(
                categorieDto.getId(),
                categorieDto.getNom(),
                categorieDto.getDescription(),
                null
        );
    }
}
