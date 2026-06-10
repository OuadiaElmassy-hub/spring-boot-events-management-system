package com.ouadia.rovista1.services.interfaces;



import com.ouadia.rovista1.entities.Utilisateur;
import com.ouadia.rovista1.exceptions.UserNotFoundException;

import java.util.List;
import java.util.Map;

public interface IUtilisateurService {

    Utilisateur editUtilisateur(Utilisateur utilisateur ,  Long id);
    Utilisateur editUtilisateurMap(  Long id , Map<String,Object> map);
    Utilisateur getUtilisateurById(  Long id )throws UserNotFoundException;
    List<Utilisateur> getAllUtilisateurs();
    void deleteUtilisateurById( Long id);
    void deleteAllByIds(Long ... ids);

}
