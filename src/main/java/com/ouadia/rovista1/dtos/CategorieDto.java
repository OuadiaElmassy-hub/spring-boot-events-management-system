package com.ouadia.rovista1.dtos;

import java.util.List;

public class CategorieDto {

    private int id;
    private String nom;
    private String description;
    private String urlPhoto;

    public CategorieDto() {
    }

    public CategorieDto(int id, String nom, String description, String urlPhoto) {
        this.id = id;
        this.nom = nom;
        this.description = description;
        this.urlPhoto = urlPhoto;
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
}
