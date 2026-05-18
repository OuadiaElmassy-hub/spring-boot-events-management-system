package com.ouadia.rovista1.services;


import com.ouadia.rovista1.entities.Categorie;

import java.util.List;

public interface ICategorieService {
    public Categorie addCategorie(Categorie categorie);
    public Categorie editCategorie(Categorie categorie);
    public Categorie getCategorieById(Long id);
    public List<Categorie> getAllCategories();
    public void deleteCategorieById(Long id);
}
