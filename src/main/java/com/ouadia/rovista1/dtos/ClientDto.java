package com.ouadia.rovista1.dtos;

import com.ouadia.rovista1.entities.Notification;
import com.ouadia.rovista1.entities.Role;
import com.ouadia.rovista1.entities.Utilisateur;
import com.ouadia.rovista1.entities.enums.StatutCompte;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;

import java.time.LocalDate;
import java.util.List;

public class ClientDto extends Utilisateur {

    private String nom;
    private String prenom;
    private LocalDate dateNaissance;

    public ClientDto() {}

    public ClientDto(String nom, String prenom, LocalDate dateNaissance) {
        this.nom = nom;
        this.prenom = prenom;
        this.dateNaissance = dateNaissance;
    }

    public ClientDto(Long id, String username, String email, String motDePasse, StatutCompte statutCompte, String phone,
                     String adresse, List<Notification> notifications, List<Role> roles, String nom, String prenom, LocalDate dateNaissance) {
        super(id, username, email, motDePasse, statutCompte, phone, adresse, notifications, roles);
        this.nom = nom;
        this.prenom = prenom;
        this.dateNaissance = dateNaissance;
    }

    public ClientDto(Long id, String username, String email, String motDePasse, StatutCompte statutCompte,
                  String phone, String adresse, String nom, String prenom, LocalDate dateNaissance) {
        super(id, username, email, motDePasse, statutCompte, phone, adresse);
        this.nom = nom;
        this.prenom = prenom;
        this.dateNaissance = dateNaissance;
    }

    public String getPrenom() {
        return prenom;
    }
    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public LocalDate getDateNaissance() {
        return dateNaissance;
    }

    public void setDateNaissance(LocalDate dateNaissance) {
        this.dateNaissance = dateNaissance;
    }
}
