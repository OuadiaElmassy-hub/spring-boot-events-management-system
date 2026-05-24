package com.ouadia.rovista1.dtos;


import jakarta.validation.constraints.NotNull;

public class VisiteurInviteDto {

    private  Long id;
    @NotNull
    private String nom;
    @NotNull
    private  String prenom;
    @NotNull
    private String email;
    @NotNull
    private String phone;
    @NotNull
    private String adresse;

    public VisiteurInviteDto() {
    }

    public VisiteurInviteDto(Long id, String nom, String prenom, String email, String phone, String adresse) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.phone = phone;
        this.adresse = adresse;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }
}
