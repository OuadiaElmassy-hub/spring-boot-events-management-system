package com.ouadia.rovista1.dtos;


import com.ouadia.rovista1.entities.Notification;
import com.ouadia.rovista1.entities.Role;
import com.ouadia.rovista1.entities.Utilisateur;
import com.ouadia.rovista1.entities.enums.StatutCompte;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data // pour les methode getter, setter, toString() , hachcode() ,equals()
@NoArgsConstructor
@Builder
public class ClientDto {

    private Long id;
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

    public ClientDto(Long id, String username, String email, String motDePasse, StatutCompte statutCompte, String phone, String adresse, String nom, String prenom, LocalDate dateNaissance) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.motDePasse = motDePasse;
        this.statutCompte = statutCompte;
        this.phone = phone;
        this.adresse = adresse;
        this.nom = nom;
        this.prenom = prenom;
        this.dateNaissance = dateNaissance;
    }


}
