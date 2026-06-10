package com.ouadia.rovista1.dtos.organisateur;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class OrgBookingDTO {
    
    Long   id;
    String clientNom;
    String clientEmail;
    String eventTitre;
    String createdAt;
    int    nbBillets;
    Double total;
    String statut;
}