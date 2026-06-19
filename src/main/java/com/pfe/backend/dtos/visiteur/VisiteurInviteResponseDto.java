package com.pfe.backend.dtos.visiteur;

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
