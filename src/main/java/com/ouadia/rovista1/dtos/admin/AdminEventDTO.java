package com.ouadia.rovista1.dtos.admin;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AdminEventDTO{
    private Long id;
    private String titre;
    private String organizerName;
    private String categorie;
    private String date;
    private String lieu;
    private Double prix;
    private String status;
    private String motif;
    private String createdAt;
}