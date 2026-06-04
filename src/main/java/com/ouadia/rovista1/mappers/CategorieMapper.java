package com.ouadia.rovista1.mappers;



import com.ouadia.rovista1.dtos.categorie.CategorieRequestDto;
import com.ouadia.rovista1.dtos.categorie.CategorieResponseDto;
import com.ouadia.rovista1.entities.Categorie;
import com.ouadia.rovista1.entities.Evenement;


public class CategorieMapper {


    public  static Categorie mappingCategorieDtoRequestToCategorie(CategorieRequestDto dto){
        return Categorie.builder()
                .nom(dto.getNom())
                .description(dto.getDescription())
                .build();
    }
    public static CategorieResponseDto mappingCategorieToCategorieDtoResponse(Categorie e){
        return CategorieResponseDto.builder()
                .nom(e.getNom())
                .description(e.getDescription())
                .evenementsId(e.getEvenements().stream().map(Evenement::getId).toList())
                .build();
    }
}
