package com.ouadia.rovista1.controllers.organisateur;

import com.ouadia.rovista1.dtos.ChangePasswordRequest;
import com.ouadia.rovista1.dtos.organisateur.OrgProfileDTO;
import com.ouadia.rovista1.dtos.organisateur.UpdateOrgProfileRequest;
import com.ouadia.rovista1.exceptions.OrganisateurNotFoundException;
import com.ouadia.rovista1.security.SecurityUtils;
import com.ouadia.rovista1.services.organisateur.OrganizerProfileService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
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