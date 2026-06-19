package com.pfe.backend.dtos.organisateur;

import com.pfe.backend.entities.enums.StatutCompte;
import com.pfe.backend.entities.enums.StatutOrganisateur;
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