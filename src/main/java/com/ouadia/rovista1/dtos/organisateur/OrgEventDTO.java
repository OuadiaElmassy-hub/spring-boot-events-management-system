package com.ouadia.rovista1.dtos.organisateur;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class OrgEventDTO {
    
    Long id;
    String titre;
    String description;
    String date;
    String lieu;
    Double prix;
    Integer capacite;
    Integer participants;
    Double  revenus;
    String  status;
    String  categorie;
    String  createdAt;
}