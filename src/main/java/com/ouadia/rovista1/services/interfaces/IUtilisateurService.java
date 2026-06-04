package com.ouadia.rovista1.services.interfaces;



import com.ouadia.rovista1.entities.Utilisateur;
import com.ouadia.rovista1.exceptions.UserNotFoundException;

import java.util.List;
import java.util.Map;

public interface IUtilisateurService {
    public Utilisateur addUtilisateur(Utilisateur utilisateur);
    public Utilisateur editUtilisateur(Utilisateur utilisateur ,  Long id);
    public Utilisateur editUtilisateurMap(  Long id , Map<String,Object> map);
    public Utilisateur getUtilisateurById(  Long id )throws UserNotFoundException;
    public List<Utilisateur> getAllUtilisateurs();
    public void deleteUtilisateurById( Long id);
    public void deleteAllByIds(Long ... ids);
}
