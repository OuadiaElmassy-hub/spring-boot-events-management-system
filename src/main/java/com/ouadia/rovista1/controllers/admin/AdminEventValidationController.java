package com.ouadia.rovista1.controllers.admin;


import com.ouadia.rovista1.dtos.admin.AdminEventDTO;
import com.ouadia.rovista1.dtos.admin.PatchEventStatusRequest;
import com.ouadia.rovista1.dtos.admin.PendingCountDTO;
import com.ouadia.rovista1.services.admin.AdminEventService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/admin/events")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class AdminEventValidationController {

    private final AdminEventService eventService;

    // GET /api/admin/events?search=&status=&categorie=&ville=&page=0&size=10
    @GetMapping
    public ResponseEntity<Page<AdminEventDTO>> getEvents(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String categorie,
            @RequestParam(required = false) String ville,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        return ResponseEntity.ok(
            eventService.searchEvents(search, status, categorie, ville, page, size));
    }

    // GET /api/admin/events/pending-count
    @GetMapping("/pending-count")
    public ResponseEntity<PendingCountDTO> getPendingCount() {
        return ResponseEntity.ok(new PendingCountDTO(eventService.countPending()));
    }

    // PATCH /api/admin/events/{id}/status
    // Body : { "status": "Approuvé", "motif": "..." }
    @PatchMapping("/{id}/status")
    public ResponseEntity<Void> patchStatus(
            @PathVariable Long id,
            @RequestBody @Valid PatchEventStatusRequest req) {

        eventService.patchStatus(id, req);
        return ResponseEntity.ok().build();
    }

}