package com.ouadia.rovista1.dtos;

import jakarta.validation.constraints.NotNull;

import java.util.List;

public class CategorieDto {

    private int id;
    @NotNull
    private String nom;
    @NotNull
    private String description;

    public CategorieDto() {
    }

    public CategorieDto(int id, String nom, String description) {
        this.id = id;

        this.nom = nom;
        this.description = description;
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


}
