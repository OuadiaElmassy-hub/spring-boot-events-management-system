package com.pfe.backend.controllers.admin;

import com.pfe.backend.dtos.admin.AdminOrganizerDTO;
import com.pfe.backend.exceptions.OrganisateurNotFoundException;
import com.pfe.backend.services.admin.AdminOrganizerService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/organisateurs")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class AdminOrganizerController {

    private final AdminOrganizerService organizerService;

    // GET /api/admin/organisateurs?search=&verified=true&page=0&size=10
    @GetMapping
    public ResponseEntity<Page<AdminOrganizerDTO>> getOrganizers(
            @RequestParam(required = false) String  search,
            @RequestParam(required = false) Boolean verified,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        return ResponseEntity.ok(
            organizerService.searchOrganizers(search, verified, page, size));
    }

    // PATCH /api/admin/organisateurs/{organisateurId}/verify
    @PatchMapping("/{organisateurId}/verify")
    public ResponseEntity<Void> verify(@PathVariable Long organisateurId) throws OrganisateurNotFoundException {
        organizerService.verify(organisateurId);
        return ResponseEntity.ok().build();
    }
}