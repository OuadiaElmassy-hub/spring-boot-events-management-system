package com.ouadia.rovista1.controllers.organisateur;

import com.ouadia.rovista1.dtos.organisateur.OrgStatisticsDTO;
import com.ouadia.rovista1.security.SecurityUtils;
import com.ouadia.rovista1.services.organisateur.OrganizerStatisticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/organisateur/statistiques")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ORGANISATEUR')")
public class OrganizerStatisticsController {

    private final OrganizerStatisticsService statisticsService;
    private final SecurityUtils              securityUtils;

    // GET /api/organisateur/statistiques
    @GetMapping
    public ResponseEntity<OrgStatisticsDTO> getStatistics() {
        return ResponseEntity.ok(
            statisticsService.getStatistics(securityUtils.getCurrentUserId()));
    }
}