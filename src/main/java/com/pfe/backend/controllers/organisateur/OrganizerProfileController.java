package com.pfe.backend.controllers.organisateur;

import com.pfe.backend.dtos.ChangePasswordRequest;
import com.pfe.backend.dtos.organisateur.OrgProfileDTO;
import com.pfe.backend.dtos.organisateur.UpdateOrgProfileRequest;
import com.pfe.backend.exceptions.OrganisateurNotFoundException;
import com.pfe.backend.security.SecurityUtils;
import com.pfe.backend.services.organisateur.OrganizerProfileService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;

@RestController
@RequestMapping("/api/organisateur/profile")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ORGANISATEUR')")
public class OrganizerProfileController {

    private final OrganizerProfileService profileService;
    private final SecurityUtils           securityUtils;

    // GET /api/organisateur/profile
    @GetMapping
    public ResponseEntity<OrgProfileDTO> getProfile() throws OrganisateurNotFoundException {
        return ResponseEntity.ok(
            profileService.getProfile(securityUtils.getCurrentUserId()));
    }

    // PUT /api/organisateur/profile
    @PutMapping
    public ResponseEntity<Void> updateProfile(
            @RequestBody @Valid UpdateOrgProfileRequest req) throws OrganisateurNotFoundException {

        profileService.updateProfile(securityUtils.getCurrentUserId(), req);
        return ResponseEntity.ok().build();
    }

    // PATCH /api/organisateur/profile/password
    @PatchMapping("/password")
    public ResponseEntity<Void> changePassword(
            @RequestBody @Valid ChangePasswordRequest req) {

        profileService.changePassword(securityUtils.getCurrentUserId(), req);
        return ResponseEntity.ok().build();
    }
}