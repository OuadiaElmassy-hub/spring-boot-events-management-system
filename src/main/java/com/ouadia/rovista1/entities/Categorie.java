package com.ouadia.rovista1.entities;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Categorie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String nom;
    private String description;
    private String urlPhoto;

    @OneToMany(mappedBy = "categorie")
    private List<Evenement> evenements;
//    @OneToOne
//    private Image image;

    public Categorie() {
    }

    public Categorie(int id, String nom, String description, String urlPhoto, List<Evenement> evenements) {
        this.id = id;
        this.nom = nom;
        this.description = description;
        this.urlPhoto = urlPhoto;
        this.evenements = evenements;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrlPhoto() {
        return urlPhoto;
    }

    public void setUrlPhoto(String urlPhoto) {
        this.urlPhoto = urlPhoto;
    }

    public List<Evenement> getEvenements() {
        return evenements;
    }

    public void setEvenements(List<Evenement> evenements) {
        this.evenements = evenements;
    }
}
