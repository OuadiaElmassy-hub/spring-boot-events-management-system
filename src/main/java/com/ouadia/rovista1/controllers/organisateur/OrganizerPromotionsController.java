package com.ouadia.rovista1.controllers.organisateur;

import com.ouadia.rovista1.dtos.organisateur.CreateUpdatePromotionRequest;
import com.ouadia.rovista1.dtos.organisateur.OrgPromotionDTO;
import com.ouadia.rovista1.dtos.organisateur.PatchPromotionStatusRequest;
import com.ouadia.rovista1.entities.Organisateur;
import com.ouadia.rovista1.exceptions.CategorieNotFoundException;
import com.ouadia.rovista1.exceptions.EventNotFoundException;
import com.ouadia.rovista1.exceptions.PromotionNotFoundException;
import com.ouadia.rovista1.repositories.OrganisateurRepository;
import com.ouadia.rovista1.security.SecurityUtils;
import com.ouadia.rovista1.services.organisateur.OrganizerPromotionsService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/organisateur/promotions")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ORGANISATEUR')")
public class OrganizerPromotionsController {

    private final OrganizerPromotionsService promotionsService;
    private final SecurityUtils         securityUtils;
    private final OrganisateurRepository organisateurRepository;

    // GET /api/organisateur/promotions?page=0&size=10&sort=createdAt,desc
    @GetMapping
    public ResponseEntity<Page<OrgPromotionDTO>> getPromotions(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Long orgId = securityUtils.getCurrentUserId();
        return ResponseEntity.ok(promotionsService.getPromotions(orgId, page, size));
    }

    // POST /api/organisateur/events
    @PostMapping
    public ResponseEntity<OrgPromotionDTO> createPromotion(
            @RequestBody @Valid CreateUpdatePromotionRequest req) throws CategorieNotFoundException, EventNotFoundException {

        Long orgId = securityUtils.getCurrentUserId();
        Organisateur organizer = organisateurRepository.findById(orgId).orElseThrow();
        OrgPromotionDTO created = promotionsService.createPromotion(orgId, req, organizer);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }


    // PUT /api/organisateur/events/{id}
    @PutMapping("/{id}")
    public ResponseEntity<OrgPromotionDTO> updatePromotion(
            @PathVariable Long id,
            @RequestBody @Valid CreateUpdatePromotionRequest req) throws CategorieNotFoundException, EventNotFoundException {

        Long orgId = securityUtils.getCurrentUserId();
        return ResponseEntity.ok(promotionsService.updatePromotion(orgId, id, req));
    }
    /** PATCH /organisateur/promotions/{id}/toggle */
    @PatchMapping("/{id}/toggle")
    @Transactional
    public ResponseEntity<Void> toggleCategorie(@PathVariable Long id) {

        try {
            promotionsService.toggleCategorie(id);
            return ResponseEntity.ok().build();
        } catch (PromotionNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // DELETE /api/organisateur/events/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePromotion(@PathVariable Long id) {
        promotionsService.deletePromotion(securityUtils.getCurrentUserId(), id);
        return ResponseEntity.noContent().build();
    }

    // PATCH /api/organisateur/events/{id}/status
    @PatchMapping("/{id}/status")
    public ResponseEntity<Void> patchStatus(
            @PathVariable Long id,
            @RequestBody @Valid PatchPromotionStatusRequest req) {

        promotionsService.patchStatus(securityUtils.getCurrentUserId(), id, req);
        return ResponseEntity.ok().build();
    }
}