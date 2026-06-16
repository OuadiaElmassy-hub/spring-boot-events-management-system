package com.ouadia.rovista1.dtos.organisateur;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class OrgEventDTO {
    
    Long id;
    String titre;
    String description;
    String date;
    String lieu;
    String ville;
    Double prix;
    Integer capacite;
    Integer participants;
    Double  revenus;
    String  status;
    String  categorie;
    String  createdAt;
}