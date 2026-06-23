package com.pfe.backend.dtos.favorie;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class HistoriqueFavorieDto {

    private Long    id;     // id de l'event (pas du favori)
    private String  titre;
    private String  date;   // ISO ou formaté
    private String  lieu;
    private Double  prix;
    private String  categorie;
}
