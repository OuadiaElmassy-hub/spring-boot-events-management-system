package com.pfe.backend.security;

import com.pfe.backend.entities.Client;
import com.pfe.backend.entities.Organisateur;
import com.pfe.backend.repositories.ClientRepository;
import com.pfe.backend.repositories.OrganisateurRepository;
import com.pfe.backend.repositories.UtilisateurRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Component
@RequiredArgsConstructor
public class SecurityUtils {

    private final UtilisateurRepository userRepository;

    public Long getCurrentUserId() {
        String username = SecurityContextHolder.getContext()
            .getAuthentication().getName();

        return userRepository.findByUsername(username)
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.UNAUTHORIZED, "Utilisateur introuvable"))
            .getId();
    }

    // Récupère directement le Client connecté
    public Client getCurrentClient(ClientRepository clientRepository) {
        Long id = getCurrentUserId();
        return clientRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.FORBIDDEN, "Profil client introuvable"));
    }

    // Récupère directement l'Organisateur connecté
    public Organisateur getCurrentOrganisateur(
            OrganisateurRepository organisateurRepository) {
        Long id = getCurrentUserId();
        return organisateurRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.FORBIDDEN, "Profil organisateur introuvable"));
    }
}