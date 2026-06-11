package com.ouadia.rovista1.controllers.client;

import com.ouadia.rovista1.dtos.ChangePasswordRequest;
import com.ouadia.rovista1.dtos.ProfileDTO;
import com.ouadia.rovista1.dtos.UpdateProfileRequest;
import com.ouadia.rovista1.security.SecurityUtils;
import com.ouadia.rovista1.services.client.ClientProfileService;
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