package com.ouadia.rovista1.dtos;

import com.ouadia.rovista1.entities.enums.Categorie;
import com.ouadia.rovista1.entities.enums.StatutEvenement;

import java.time.LocalDate;

public class EvenementDtoAdd {

    private String titre;
    private String description;
    private LocalDate date;
    private String lieu; //ville
    private Categorie categorie;
    private int capacite;
    private double prix; // deplacer le car depend du type.
    private StatutEvenement statut;

    public EvenementDtoAdd() {}

    public EvenementDtoAdd(String titre, String description, LocalDate date, String lieu,
                           Categorie categorie, int capacite, double prix, StatutEvenement statut) {
        this.titre = titre;
        this.description = description;
        this.date = date;
        this.lieu = lieu;
        this.categorie = categorie;
        this.capacite = capacite;
        this.prix = prix;
        this.statut = statut;
    }

    public String getTitre() { return titre; }
    public void setTitre(String titre) { this.titre = titre; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }

    public String getLieu() { return lieu; }
    public void setLieu(String lieu) { this.lieu = lieu; }

    public Categorie getCategorie() {
        return categorie;
    }

    public void setCategorie(Categorie categorie) {
        this.categorie = categorie;
    }

    public int getCapacite() { return capacite; }
    public void setCapacite(int capacite) { this.capacite = capacite; }

    public double getPrix() { return prix; }
    public void setPrix(double prix) { this.prix = prix; }

    public StatutEvenement getStatut() { return statut; }
    public void setStatut(StatutEvenement statut) { this.statut = statut; }

}
