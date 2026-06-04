package com.ouadia.rovista1.dtos.organisateur;

import com.ouadia.rovista1.entities.enums.StatutCompte;
import com.ouadia.rovista1.entities.enums.StatutOrganisateur;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrganisateurResponseDto {
    private Long id;
    private String email;
    private String username;
    private String motDePasse;
    private StatutCompte statutCompte;
    private String phone;
    private String adresse;
    private String nomOrganisation;
    private Long numRegistre;
    private StatutOrganisateur statutOrganisateur;
}