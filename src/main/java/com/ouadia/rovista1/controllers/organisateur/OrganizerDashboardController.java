package com.ouadia.rovista1.controllers.organisateur;

import com.ouadia.rovista1.dtos.organisateur.OrgDashboardStatsDTO;
import com.ouadia.rovista1.exceptions.OrganisateurNotFoundException;
import com.ouadia.rovista1.security.SecurityUtils;
import com.ouadia.rovista1.services.organisateur.OrganizerDashboardService;
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