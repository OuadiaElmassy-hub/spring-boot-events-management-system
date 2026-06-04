package com.ouadia.rovista1.dtos.categorie;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data // pour les methode getter, setter, toString() , hachcode() ,equals()
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategorieRequestDto {
    private String nom;
    private String description;
}
