package com.pfe.backend.services.interfaces;



import com.pfe.backend.entities.Utilisateur;
import com.pfe.backend.exceptions.UserNotFoundException;

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
