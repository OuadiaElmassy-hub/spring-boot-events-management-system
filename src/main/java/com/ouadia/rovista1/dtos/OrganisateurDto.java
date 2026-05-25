package com.ouadia.rovista1.dtos;

import com.ouadia.rovista1.entities.Utilisateur;
import com.ouadia.rovista1.entities.enums.StatutCompte;
import com.ouadia.rovista1.entities.enums.StatutOrganisateur;
import jakarta.validation.constraints.NotNull;

public class OrganisateurDto  {
    private Long id;
    @NotNull
    private String email;
    @NotNull
    private String username;
    @NotNull
    private String motDePasse;
    @NotNull
    private StatutCompte statutCompte;
    @NotNull
    private String phone;
    @NotNull
    private String adresse;
    @NotNull
    private String nomOrganisation;
    @NotNull
    private int numRegistre;
    @NotNull
    private StatutOrganisateur statutOrganisateur;

    public OrganisateurDto() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMotDePasse() {
        return motDePasse;
    }

    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }

    public StatutCompte getStatutCompte() {
        return statutCompte;
    }

    public void setStatutCompte(StatutCompte statutCompte) {
        this.statutCompte = statutCompte;
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



    public OrganisateurDto(Long id, String username, String email,String motDePasse, String phone, String adresse, StatutCompte statutCompte, String nomOrganisation, int numRegistre, StatutOrganisateur statutOrganisateur) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.phone = phone;
        this.motDePasse = motDePasse;
        this.adresse = adresse;
        this.statutCompte = statutCompte;
        this.nomOrganisation = nomOrganisation;
        this.numRegistre = numRegistre;
        this.statutOrganisateur = statutOrganisateur;
    }

    public int getNumRegistre() {
        return numRegistre;
    }

    public void setNumRegistre(int numRegistre) {
        this.numRegistre = numRegistre;
    }

    public String getNomOrganisation() {
        return nomOrganisation;
    }

    public void setNomOrganisation(String nomOrganisation) {
        this.nomOrganisation = nomOrganisation;
    }

    public StatutOrganisateur getStatutOrganisateur() { return statutOrganisateur; }
    public void setStatutOrganisateur(StatutOrganisateur statutOrganisateur) { this.statutOrganisateur = statutOrganisateur; }
}
