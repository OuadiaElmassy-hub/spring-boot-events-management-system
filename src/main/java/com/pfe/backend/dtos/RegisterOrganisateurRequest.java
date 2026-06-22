package com.pfe.backend.dtos;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class RegisterOrganisateurRequest{

    String nom;
    String prenom;
    String username;
    String email;
    String password;
    String phone;
    String adresse;
    String organisationNom;
    String siret;
    Long numRegister;
}