package com.ouadia.rovista1.Mapper;

import com.ouadia.rovista1.dtos.UtilisateurDto;
import com.ouadia.rovista1.entities.Client;
import com.ouadia.rovista1.entities.Utilisateur;

public class UtilisateurMapper {
    public static UtilisateurDto mapToUtilisateurDto(Utilisateur utilisateur){
        return new UtilisateurDto(
                utilisateur.getId(),
                utilisateur.getUsername(),
                utilisateur.getEmail(),
                utilisateur.getMotDePasse(),
                utilisateur.getStatutCompte(),
                utilisateur.getPhone(),
                utilisateur.getAdresse()
        );
    }
    public static Client mapToUtilisateur(UtilisateurDto utilisateurDto){
        return new Client(
                utilisateurDto.getId(),
                utilisateurDto.getUsername(),
                utilisateurDto.getEmail(),
                utilisateurDto.getMotDePasse(),
                utilisateurDto.getStatutCompte(),
                utilisateurDto.getPhone(),
                utilisateurDto.getAdresse()
        );
    }

}
