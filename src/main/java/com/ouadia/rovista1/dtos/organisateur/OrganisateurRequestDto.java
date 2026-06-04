package com.ouadia.rovista1.dtos.organisateur;

import com.ouadia.rovista1.entities.enums.StatutCompte;
import com.ouadia.rovista1.entities.enums.StatutOrganisateur;
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