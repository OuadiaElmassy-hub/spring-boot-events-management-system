package com.pfe.backend.controllers.client;

import com.pfe.backend.dtos.ChangePasswordRequest;
import com.pfe.backend.dtos.ProfileDTO;
import com.pfe.backend.dtos.UpdateProfileRequest;
import com.pfe.backend.security.SecurityUtils;
import com.pfe.backend.services.client.ClientProfileService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/client/profile")
@RequiredArgsConstructor
public class ClientProfileController {

    private final ClientProfileService profileService;
    private final SecurityUtils securityUtils;

    // GET /api/client/profile
    @GetMapping
    public ResponseEntity<ProfileDTO> getProfile(
            @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(
            profileService.getProfile(securityUtils.getCurrentUserId()));
    }

    // PUT /api/client/profile
    @PutMapping
    public ResponseEntity<Void> updateProfile(
            @RequestBody @Valid UpdateProfileRequest req,
            @AuthenticationPrincipal UserDetails userDetails) {

        profileService.updateProfile(securityUtils.getCurrentUserId(), req);
        return ResponseEntity.ok().build();
    }

    // PATCH /api/client/profile/password
    @PatchMapping("/password")
    public ResponseEntity<Void> changePassword(
            @RequestBody @Valid ChangePasswordRequest req,
            @AuthenticationPrincipal UserDetails userDetails) {

        profileService.changePassword(securityUtils.getCurrentUserId(), req);
        return ResponseEntity.ok().build();
    }
}