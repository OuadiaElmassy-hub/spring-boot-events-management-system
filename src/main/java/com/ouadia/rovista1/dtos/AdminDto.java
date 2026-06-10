package com.ouadia.rovista1.dtos;

import com.ouadia.rovista1.entities.Notification;
import com.ouadia.rovista1.entities.Role;
import com.ouadia.rovista1.entities.Utilisateur;
import com.ouadia.rovista1.entities.enums.StatutCompte;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.List;


public class AdminDto extends Utilisateur {
    @NotNull
    private String nom;
    @NotNull
    private String prenom;
    @NotNull
    private LocalDate dateNaissance;

    public AdminDto(String nom, String prenom, LocalDate dateNaissance) {
        this.nom = nom;
        this.prenom = prenom;
        this.dateNaissance = dateNaissance;
    }

    public AdminDto() {
    }

    public AdminDto(Long id, String username, String email, String motDePasse, StatutCompte statutCompte, String phone,
                    String adresse, List<Notification> notifications, List<Role> roles, String nom, String prenom,
                    LocalDate dateNaissance) {
        super(id, username, nom, email, motDePasse, statutCompte, phone, adresse, notifications, roles);
        this.nom = nom;
        this.prenom = prenom;
        this.dateNaissance = dateNaissance;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public LocalDate getDateNaissance() {
        return dateNaissance;
    }

    public void setDateNaissance(LocalDate dateNaissance) {
        this.dateNaissance = dateNaissance;
    }
}
