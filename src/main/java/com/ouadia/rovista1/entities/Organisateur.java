package com.ouadia.rovista1.entities;

import com.ouadia.rovista1.entities.enums.StatutCompte;
import com.ouadia.rovista1.entities.enums.StatutOrganisateur;
import jakarta.persistence.*;

import java.util.List;
@Entity
public class Organisateur extends Utilisateur{

    private String nomOrganisation;
    private int numRegistre;
    @Enumerated(EnumType.STRING)
    private StatutOrganisateur statutOrganisateur;

    @OneToMany(mappedBy = "organisateur")
    private List<Evenement> evenements;
    @OneToMany(mappedBy = "organisateur")
    private List<Promotion> promotions;

    public Organisateur() {}

    public Organisateur(Long id, String email, String username, String motDePasse,
                        StatutCompte statutCompte, String adresse, String telephonePro,
                        String nomOrganisation, int numRegistre, StatutOrganisateur statutOrganisateur,
                        List<Evenement> evenements, List<Promotion> promotions) {
        super(id, username, email, motDePasse, statutCompte, telephonePro, adresse);
        this.numRegistre = numRegistre;
        this.statutOrganisateur = statutOrganisateur;
        this.nomOrganisation = nomOrganisation;
        this.promotions = promotions;
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

    public List<Evenement> getEvenements() {
        return evenements;
    }

    public void setEvenements(List<Evenement> evenements) {
        this.evenements = evenements;
    }

    public StatutOrganisateur getStatutOrganisateur() { return statutOrganisateur; }
    public void setStatutOrganisateur(StatutOrganisateur statutOrganisateur) { this.statutOrganisateur = statutOrganisateur; }

    public List<Promotion> getPromotions() {
        return promotions;
    }

    public void setPromotions(List<Promotion> promotions) {
        this.promotions = promotions;
    }
}
