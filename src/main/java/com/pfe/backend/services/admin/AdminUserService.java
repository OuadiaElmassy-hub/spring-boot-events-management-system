package com.pfe.backend.services.admin;

import com.pfe.backend.dtos.admin.AdminUserDTO;
import com.pfe.backend.dtos.admin.PatchUserStatusRequest;
import com.pfe.backend.entities.Client;
import com.pfe.backend.entities.Organisateur;
import com.pfe.backend.entities.Utilisateur;
import com.pfe.backend.exceptions.UtilisateurNotFoundException;
import com.pfe.backend.repositories.UtilisateurRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.context.support.BeanDefinitionDsl;
import org.springframework.context.support.BeanDefinitionDsl.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminUserService {

    private final UtilisateurRepository userRepo;

    public Page<AdminUserDTO> searchUsers(
            String search, String role, int page, int size) {

        Pageable pageable = PageRequest.of(page, size);
        Role roleEnum = null;
        if (role != null && !role.isBlank() && !role.equals("Tous")) {
            try { roleEnum = BeanDefinitionDsl.Role.valueOf(role); }
            catch (IllegalArgumentException ignored) {}
        }

        return userRepo.search(
            nullIfBlank(search), roleEnum, pageable
        ).map(this::toDTO);
    }

    @Transactional
    public void patchStatus(Long userId, PatchUserStatusRequest req) throws UtilisateurNotFoundException {
        Utilisateur u = userRepo.findById(userId)
            .orElseThrow(() -> new UtilisateurNotFoundException("Utilisateur introuvable with id : " + userId));

        u.setEnabled("Actif".equals(req.getStatus()));
        userRepo.save(u);
    }

    private AdminUserDTO toDTO(Utilisateur u) {

        return new AdminUserDTO(
                u.getId(),
                u.getEmail(),
                u.getNom(),
                u.getRoles().stream()
                    .map(com.pfe.backend.entities.Role::getRoleName)   // extraire le nom
                    .collect(Collectors.toList()),
                u.isEnabled() ? "Actif" : "Inactif",
                u.getCreatedAt() != null ? u.getCreatedAt().toString() : null,
                u instanceof Client c   ? c.getAvatar()   : u instanceof Organisateur o ? o.getAvatar()   : null
        );
    }

    private String nullIfBlank(String s) {
        return (s == null || s.isBlank()) ? null : s;
    }

    public void delete(Long id) {
        userRepo.deleteById(id);
    }
}