package com.pfe.backend.controllers.client;

import com.pfe.backend.dtos.SettingsDTO;
import com.pfe.backend.dtos.UpdateSettingRequest;
import com.pfe.backend.security.SecurityUtils;
import com.pfe.backend.services.client.ClientSettingsService;
import org.springframework.web.bind.annotation.RestController;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/client/settings")
@RequiredArgsConstructor
public class ClientSettingsController {

    private final ClientSettingsService settingsService;
    private final SecurityUtils securityUtils;

    // GET /api/client/settings
    @GetMapping
    public ResponseEntity<SettingsDTO> getSettings(
            @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(
            settingsService.getSettings(securityUtils.getCurrentUserId()));
    }

    // PATCH /api/client/settings
    @PatchMapping
    public ResponseEntity<Void> updateSetting(
            @RequestBody @Valid UpdateSettingRequest req,
            @AuthenticationPrincipal UserDetails userDetails) {

        settingsService.updateSetting(securityUtils.getCurrentUserId(), req);
        return ResponseEntity.ok().build();
    }
}