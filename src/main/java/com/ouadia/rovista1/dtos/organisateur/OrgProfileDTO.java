package com.ouadia.rovista1.dtos.organisateur;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class OrgProfileDTO {

    String  organisationNom;
    String  email;
    String  telephone;
    String  ville;
    String  siret;
    String  avatar;
    boolean verified;
    String  createdAt;
}