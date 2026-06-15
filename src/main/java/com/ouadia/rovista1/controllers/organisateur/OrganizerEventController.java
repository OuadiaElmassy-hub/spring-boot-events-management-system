package com.ouadia.rovista1.controllers.organisateur;

import com.ouadia.rovista1.dtos.organisateur.CreateUpdateEventRequest;
import com.ouadia.rovista1.dtos.organisateur.OrgEventDTO;
import com.ouadia.rovista1.dtos.organisateur.PatchEventStatusRequest;
import com.ouadia.rovista1.entities.Evenement;
import com.ouadia.rovista1.entities.Organisateur;
import com.ouadia.rovista1.exceptions.CategorieNotFoundException;
import com.ouadia.rovista1.exceptions.EventNotFoundException;
import com.ouadia.rovista1.repositories.OrganisateurRepository;
import com.ouadia.rovista1.security.MyUserDetails;
import com.ouadia.rovista1.security.SecurityUtils;
import com.ouadia.rovista1.services.FileStorageService;
import com.ouadia.rovista1.services.organisateur.OrganizerEventService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/organisateur/events")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ORGANISATEUR')")
public class OrganizerEventController {

    private final OrganizerEventService eventService;
    private final SecurityUtils         securityUtils;
    private final OrganisateurRepository organisateurRepository;

    // GET /api/organisateur/events?search=&status=&page=0&size=10&sort=createdAt,desc
    @GetMapping
    public ResponseEntity<Page<OrgEventDTO>> getEvents(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Long orgId = securityUtils.getCurrentUserId();
        return ResponseEntity.ok(eventService.getEvents(orgId, search, status, page, size));
    }

    // POST /api/organisateur/events
    @PostMapping
    public ResponseEntity<OrgEventDTO> createEvent(
            @RequestBody @Valid CreateUpdateEventRequest req) throws CategorieNotFoundException {

        Long orgId = securityUtils.getCurrentUserId();
        Organisateur organizer = organisateurRepository.findById(orgId).orElseThrow();
        OrgEventDTO created = eventService.createEvent(orgId, req, organizer);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }


    @PostMapping("/{eventId}/images")
        public ResponseEntity<?> uploadImage(
                @PathVariable Long eventId,
                @AuthenticationPrincipal MyUserDetails user,
                @RequestParam List<MultipartFile> images){
        try {
            eventService.storeEventImages(user.getId(), eventId, images );
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (EventNotFoundException e) {
            return ResponseEntity.badRequest().build();
        } catch (IOException e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    // PUT /api/organisateur/events/{id}
    @PutMapping("/{id}")
    public ResponseEntity<OrgEventDTO> updateEvent(
            @PathVariable Long id,
            @RequestBody @Valid CreateUpdateEventRequest req) throws CategorieNotFoundException {

        Long orgId = securityUtils.getCurrentUserId();
        return ResponseEntity.ok(eventService.updateEvent(orgId, id, req));
    }

    // DELETE /api/organisateur/events/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEvent(@PathVariable Long id) {
        eventService.deleteEvent(securityUtils.getCurrentUserId(), id);
        return ResponseEntity.noContent().build();
    }

    // PATCH /api/organisateur/events/{id}/status
    @PatchMapping("/{id}/status")
    public ResponseEntity<Void> patchStatus(
            @PathVariable Long id,
            @RequestBody @Valid PatchEventStatusRequest req) {

        eventService.patchStatus(securityUtils.getCurrentUserId(), id, req);
        return ResponseEntity.ok().build();
    }
}