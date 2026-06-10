package com.ouadia.rovista1.services.organisateur;


import com.ouadia.rovista1.dtos.ChangePasswordRequest;
import com.ouadia.rovista1.dtos.organisateur.OrgProfileDTO;
import com.ouadia.rovista1.dtos.organisateur.UpdateOrgProfileRequest;
import com.ouadia.rovista1.entities.Organisateur;
import com.ouadia.rovista1.exceptions.OrganisateurNotFoundException;
import com.ouadia.rovista1.repositories.OrganisateurRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class OrganizerProfileService {

    private final OrganisateurRepository organisateurRepository;
    private final PasswordEncoder passwordEncoder;

    public OrgProfileDTO getProfile(Long userId) throws OrganisateurNotFoundException {
        Organisateur op = organisateurRepository.findById(userId)
                .orElseThrow(() -> new OrganisateurNotFoundException("Organisateur not found with id : "+userId));

        return new OrgProfileDTO(
            op.getNom(),
            op.getEmail(),
            op.getPhone(),
            op.getAdresse(),
            op.getSiret(),
            op.getAvatar(),
            op.isVerified(),
            op.getCreatedAt() != null ? op.getCreatedAt().toString() : null
        );
    }

    @Transactional
    public void updateProfile(Long userId, UpdateOrgProfileRequest req) throws OrganisateurNotFoundException {

        // il faut verifier les champs ne sont pas vides !!!!!

        Organisateur org = organisateurRepository.findById(userId)
                .orElseThrow(() -> new OrganisateurNotFoundException("Organisateur not found with id : "+userId));

        // Vérification unicité email
        if (!org.getEmail().equals(req.getEmail())
                && organisateurRepository.existsByEmail(req.getEmail())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email déjà utilisé");
        }

        // Champs Utilisateur (hérités)
        org.setNom(req.getNom());
        org.setEmail(req.getEmail());
        org.setPhone(req.getTelephone());
        org.setAdresse(req.getVille());

        // Profil organisateur
        org.setLogoUrl(req.getLogoUrl());
        org.setNumRegistre(req.getNumRegister());
        org.setSiret(req.getSiret());
        organisateurRepository.save(org);
    }

    @Transactional
    public void changePassword(Long userId, ChangePasswordRequest req) {
        Organisateur u = organisateurRepository.findById(userId).orElseThrow();
        if (!passwordEncoder.matches(req.getCurrentPassword(), u.getMotDePasse())) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST, "Mot de passe actuel incorrect");
        }
        u.setMotDePasse(passwordEncoder.encode(req.getNewPassword()));
        organisateurRepository.save(u);
    }
}