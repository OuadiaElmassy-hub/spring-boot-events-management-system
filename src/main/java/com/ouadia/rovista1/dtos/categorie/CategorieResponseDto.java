package com.ouadia.rovista1.dtos.categorie;

import com.ouadia.rovista1.entities.Evenement;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.List;
@Data // pour les methode getter, setter, toString() , hachcode() ,equals()
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategorieResponseDto {
    private Long id;
    private String nom;
    private String description;
    private List<Long> evenementsId;
}
