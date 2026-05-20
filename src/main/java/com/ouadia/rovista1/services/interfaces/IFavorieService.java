package com.ouadia.rovista1.services.interfaces;


import com.ouadia.rovista1.entities.Favorie;

import java.util.List;

public interface IFavorieService {
    public Favorie addFavorie(Favorie favorie);
    public Favorie editFavorie(Favorie favorie);
    public Favorie getFavorieById(Long id);
    public List<Favorie> getAllFavories();
    public void deleteFavorieById(Long id);
}
