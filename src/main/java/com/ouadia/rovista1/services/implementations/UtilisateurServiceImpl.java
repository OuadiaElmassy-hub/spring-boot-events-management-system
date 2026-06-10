package com.ouadia.rovista1.services.implementations;

import com.ouadia.rovista1.entities.Utilisateur;
import com.ouadia.rovista1.exceptions.UserNotFoundException;
import com.ouadia.rovista1.repositories.UtilisateurRepository;
import com.ouadia.rovista1.security.JwtService;
import com.ouadia.rovista1.services.interfaces.IUtilisateurService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
