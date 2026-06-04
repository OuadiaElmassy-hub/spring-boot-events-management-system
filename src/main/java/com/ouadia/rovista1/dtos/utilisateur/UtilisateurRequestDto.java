package com.ouadia.rovista1.dtos.utilisateur;

import com.ouadia.rovista1.entities.enums.StatutCompte;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class UtilisateurRequestDto {

    private String email;
    private String username;
    private String motDePasse;
    private StatutCompte statutCompte;
    private String phone;
    private String adresse;
}
