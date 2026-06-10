package com.ouadia.rovista1.dtos.favorie;

import lombok.Builder;

@Builder
public class HistoriqueFavorieDto {

    private Long    id;     // id de l'event (pas du favori)
    private String  titre;
    private String  date;   // ISO ou formaté
    private String  lieu;
    private Double  prix;
    private String  categorie;
}
