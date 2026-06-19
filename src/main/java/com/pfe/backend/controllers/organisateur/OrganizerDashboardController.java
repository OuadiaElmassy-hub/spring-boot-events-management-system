package com.pfe.backend.controllers.organisateur;

import com.pfe.backend.dtos.organisateur.OrgDashboardStatsDTO;
import com.pfe.backend.exceptions.OrganisateurNotFoundException;
import com.pfe.backend.security.SecurityUtils;
import com.pfe.backend.services.organisateur.OrganizerDashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/organisateur")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ORGANISATEUR')")
public class OrganizerDashboardController {

    private final OrganizerDashboardService dashboardService;
    private final SecurityUtils securityUtils;

    // GET /api/organisateur/dashboard/stats
    @GetMapping("/dashboard/stats")
    public ResponseEntity<OrgDashboardStatsDTO> getStats() throws OrganisateurNotFoundException {
        return ResponseEntity.ok(
            dashboardService.getStats(securityUtils.getCurrentUserId()));
    }
}