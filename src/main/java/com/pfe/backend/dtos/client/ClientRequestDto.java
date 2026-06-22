package com.pfe.backend.dtos.client;

import com.pfe.backend.entities.enums.StatutCompte;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;



@Data // pour les methode getter, setter, toString() , hachcode() ,equals()
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClientRequestDto {
    @NotNull
    private String username;
    @NotNull
    private String email;
    @NotNull
    private String motDePasse;
    private StatutCompte statutCompte;
    @NotNull
    private String phone;
    @NotNull
    private String adresse;
    @NotNull
    private String nom;
    @NotNull
    private String prenom;
    @NotNull
    private LocalDate dateNaissance;
}
