package com.pfe.backend.services.implementations;

import com.pfe.backend.entities.Utilisateur;
import com.pfe.backend.exceptions.UserNotFoundException;
import com.pfe.backend.repositories.UtilisateurRepository;
import com.pfe.backend.security.JwtService;
import com.pfe.backend.services.interfaces.IUtilisateurService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
@Service
@RequiredArgsConstructor
public class UtilisateurServiceImpl implements IUtilisateurService {

    UtilisateurRepository repo;

    private AuthenticationManager authManager;
    private JwtService jwtService;

    @Override
    public Utilisateur editUtilisateur(Utilisateur utilisateur, Long id) {
        return null;
    }

    @Override
    public Utilisateur editUtilisateurMap(Long id, Map<String, Object> map) {
        return null;
    }

    @Override
    public Utilisateur getUtilisateurById(Long id) throws UserNotFoundException {
        return null;
    }

    @Override
    public List<Utilisateur> getAllUtilisateurs() {
        return List.of();
    }

    @Override
    public void deleteUtilisateurById(Long id) {

    }

    @Override
    public void deleteAllByIds(Long... ids) {

    }


}
