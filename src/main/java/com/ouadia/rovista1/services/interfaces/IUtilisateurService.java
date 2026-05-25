package com.ouadia.rovista1.services.interfaces;


import com.ouadia.rovista1.dtos.UtilisateurDto;
import com.ouadia.rovista1.exceptions.UserNotFoundException;

import java.util.List;
import java.util.Map;

public interface IUtilisateurService {
    public UtilisateurDto addUtilisateur(UtilisateurDto utilisateurDto);
    public UtilisateurDto editUtilisateur(UtilisateurDto utilisateurDto ,  Long id);
    public UtilisateurDto editUtilisateurMap(  Long id , Map<String,Object> map);
    public UtilisateurDto getUtilisateurById(  Long id )throws UserNotFoundException;
    public List<UtilisateurDto> getAllUtilisateurs();
    public void deleteUtilisateurById( Long id);
    public void deleteAllByIds(Long ... ids);
}
