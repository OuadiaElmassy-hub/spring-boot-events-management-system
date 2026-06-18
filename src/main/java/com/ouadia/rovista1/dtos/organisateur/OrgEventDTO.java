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
    String dateDebut;
    String dateFin;
    String lieuSpecifique;
    String ville;
    Double prix;
    Double prixVIP;
    Integer capacite;
    Integer nbPlacesVIP;
    Integer participants;
    Double  revenus;
    String  status;
    String  categorie;
    String  createdAt;
}