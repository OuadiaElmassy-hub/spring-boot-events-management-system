package com.ouadia.rovista1.dtos.visiteur;

import com.ouadia.rovista1.entities.Avis;
import com.ouadia.rovista1.entities.Reservation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;


import java.util.List;
@Data
@AllArgsConstructor
@Builder
public class VisiteurInviteResponseDto {
    private  Long id;
    private String nom;
    private  String prenom;
    private String email;
    private String phone;
    private String adresse;
    private List<Long> reservationsId;
    private List<Long> avisId;
}
