package com.ouadia.rovista1.services.interfaces;


import com.ouadia.rovista1.entities.Utilisateur;

import java.util.List;

public interface IUtilisateurService {
    public Utilisateur addUtilisateur(Utilisateur utilisateur);
    public Utilisateur editUtilisateur(Utilisateur utilisateur);
    public Utilisateur getUtilisateurById(Long id);
    public List<Utilisateur> getAllUtilisateurs();
    public void deleteUtilisateurById(Long id);
}
