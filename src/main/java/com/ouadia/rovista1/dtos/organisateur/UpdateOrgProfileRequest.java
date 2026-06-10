package com.ouadia.rovista1.dtos.organisateur;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UpdateOrgProfileRequest {

    @NotBlank String nom;
    @Email
    String email;
    String telephone;
    String ville;
    @NotBlank
    String organisationNom;
    String siret;
    String logoUrl;
    Long numRegister;
}