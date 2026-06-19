package com.pfe.backend.dtos.organisateur;

import com.pfe.backend.entities.enums.StatutCompte;
import com.pfe.backend.entities.enums.StatutOrganisateur;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrganisateurRequestDto {
    @NotNull
    private String email;
    @NotNull
    private String username;
    @NotNull
    private String motDePasse;
    @NotNull
    private StatutCompte statutCompte;
    @NotNull
    private String phone;
    @NotNull
    private String adresse;
    @NotNull
    private String nomOrganisation;
    @NotNull
    private Long numRegistre;
    @NotNull
    private StatutOrganisateur statutOrganisateur;
}