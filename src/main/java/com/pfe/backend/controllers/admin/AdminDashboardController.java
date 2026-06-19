package com.pfe.backend.controllers.admin;

import com.pfe.backend.dtos.admin.AdminDetailedStatsDTO;
import com.pfe.backend.dtos.admin.AdminStatsDTO;
import com.pfe.backend.services.admin.AdminDashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class AdminDashboardController {

    private final AdminDashboardService dashboardService;

    // GET /api/admin/statistiques
    @GetMapping("/statistiques")
    public ResponseEntity<AdminStatsDTO> getStats() {
        return ResponseEntity.ok(dashboardService.getStats());
    }

    // GET /api/admin/statistiques/detailed
    @GetMapping("/statistiques/detailed")
    public ResponseEntity<AdminDetailedStatsDTO> getDetailedStats() {
        return ResponseEntity.ok(dashboardService.getDetailedStats());
    }
}
