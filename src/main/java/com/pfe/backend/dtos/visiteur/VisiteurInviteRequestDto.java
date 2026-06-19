package com.pfe.backend.dtos.visiteur;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class VisiteurInviteRequestDto {

    private String nom;
    private  String prenom;
    private String email;
    private String phone;

}
