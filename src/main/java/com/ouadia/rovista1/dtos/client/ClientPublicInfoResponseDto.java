package com.ouadia.rovista1.dtos.client;

import com.ouadia.rovista1.entities.enums.StatutCompte;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClientPublicInfoResponseDto {

    private String email;
    private String phone;
    private String adresse;
    private String nom;
    private String prenom;
    private LocalDate dateNaissance;
}
