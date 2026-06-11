package com.ouadia.rovista1.controllers.organisateur;

import com.ouadia.rovista1.dtos.organisateur.CreateUpdateEventRequest;
import com.ouadia.rovista1.dtos.organisateur.OrgEventDTO;
import com.ouadia.rovista1.dtos.organisateur.PatchEventStatusRequest;
import com.ouadia.rovista1.entities.Organisateur;
import com.ouadia.rovista1.exceptions.CategorieNotFoundException;
import com.ouadia.rovista1.repositories.OrganisateurRepository;
import com.ouadia.rovista1.security.SecurityUtils;
import com.ouadia.rovista1.services.organisateur.OrganizerEventService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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

    /*
        @PostMapping("/{eventId}/images")
        public void uploadImage(
                @PathVariable Long eventId,
                @AuthenticationPrincipal MyUserDetails user,
                @RequestParam MultipartFile image){
            eventImageService.upload(user.getId(), eventId, image );
        }

        Service :
         public void upload(Long orgId, Long eventId, MultipartFile image ){

            Evenement event = eventRepo .findByIdAndOrganisateurId( eventId, orgId )
                            .orElseThrow(() -> new ResponseStatusException(
                            HttpStatus.FORBIDDEN));
          // upload image
        }

        Ainsi :
        Organisateur A
            ↓
        Upload image événement A
            OK
        Organisateur A
            ↓
        Upload image événement B
            403 Forbidden
    */

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