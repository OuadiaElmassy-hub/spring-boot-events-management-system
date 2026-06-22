package com.pfe.backend.controllers.organisateur;

import com.pfe.backend.dtos.organisateur.OrgStatisticsDTO;
import com.pfe.backend.security.SecurityUtils;
import com.pfe.backend.services.organisateur.OrganizerStatisticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;

@RestController
@RequestMapping("/api/organisateur/statistiques")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ORGANISATEUR')")
public class OrganizerStatisticsController {

    private final OrganizerStatisticsService statistics;
    private final SecurityUtils              securityUtils;
    private  OrgStatisticsDTO statsDto;
    // GET /api/organisateur/statistiques
    @GetMapping
    public ResponseEntity<OrgStatisticsDTO> getStatistics() {

        Long orgId = securityUtils.getCurrentUserId();
        OrgStatisticsDTO statsDto = statistics.getStatistics(orgId);

        return ResponseEntity.ok(statsDto);
    }

}