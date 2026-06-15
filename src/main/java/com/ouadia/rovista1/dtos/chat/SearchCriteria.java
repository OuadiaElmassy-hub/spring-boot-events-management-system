package com.ouadia.rovista1.dtos.chat;

import lombok.Data;

@Data
public class SearchCriteria {
    private String ville;
    private String categorie;
    private Double prixMax;
    private String keyword;
    private Long categorieId;
}
