package com.pfe.backend.services.client;

import com.pfe.backend.dtos.ChangePasswordRequest;
import com.pfe.backend.dtos.ProfileDTO;
import com.pfe.backend.dtos.UpdateProfileRequest;
import com.pfe.backend.entities.Client;
import com.pfe.backend.repositories.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class ClientProfileService {

    private final ClientRepository clientRepo;
    private final PasswordEncoder passwordEncoder;

    public ProfileDTO getProfile(Long userId) {
        Client client = clientRepo.findById(userId).orElseThrow();
        return ProfileDTO.builder()
                .nom(client.getNom())
                .email(client.getEmail())
                .telephone(client.getPhone())
                .ville(client.getAdresse())
                .avatar(client.getAvatar())
                .createdAt(client.getCreatedAt().toString())
                .build();
    }

    @Transactional
    public void updateProfile(Long userId, UpdateProfileRequest req) {
        Client c = clientRepo.findById(userId).orElseThrow();
        // Vérifier unicité email si changé
        if (!c.getEmail().equals(req.getEmail())
            && clientRepo.existsByEmail(req.getEmail())) {
            throw new ResponseStatusException(
                HttpStatus.CONFLICT, "Email déjà utilisé");
        }
        c.setNom(req.getNom());
        c.setEmail(req.getEmail());
        c.setPhone(req.getTelephone());
        c.setAdresse(req.getVille());
        c.setPrenom(req.getPrenom());

        clientRepo.save(c);
    }

    @Transactional
    public void changePassword(Long clientId, ChangePasswordRequest req) {
        Client c = clientRepo.findById(clientId).orElseThrow();

        if (!passwordEncoder.matches(req.getCurrentPassword(), c.getMotDePasse())) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST, "Mot de passe actuel incorrect");
        }
        c.setMotDePasse(passwordEncoder.encode(req.getNewPassword()));
        clientRepo.save(c);
    }
}