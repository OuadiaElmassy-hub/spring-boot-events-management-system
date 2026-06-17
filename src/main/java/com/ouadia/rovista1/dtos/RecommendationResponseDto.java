package com.ouadia.rovista1.dtos;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class RecommendationResponseDto {

    private Long id;
    private String  titre;
    private String  date;
    private String  lieu;
    private Double  prix;
    private int     score;           // calculé ou statique
    private boolean isFavorite;    // nombre d'événements ajoutés en favoris
}
