package com.pfe.backend.dtos.client;

import lombok.*;

import java.time.LocalDate;

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
