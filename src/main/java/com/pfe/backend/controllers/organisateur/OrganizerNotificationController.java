package com.pfe.backend.controllers.organisateur;

import com.pfe.backend.dtos.admin.UnreadCountDTO;
import com.pfe.backend.dtos.organisateur.OrganisateurNotificationDTO;
import com.pfe.backend.security.SecurityUtils;
import com.pfe.backend.services.organisateur.OrganizerNotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;

@RestController
@RequestMapping("/api/organisateur/notifications")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ORGANISATEUR')")
public class OrganizerNotificationController {

    private final OrganizerNotificationService notifService;
    private final SecurityUtils                securityUtils;

    // GET /api/organisateur/notifications?page=0&size=15&sort=createdAt,desc
    @GetMapping
    public ResponseEntity<Page<OrganisateurNotificationDTO>> getNotifications(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Long orgId = securityUtils.getCurrentUserId();
        return ResponseEntity.ok(notifService.getNotifications(orgId, page, size));
    }

    // GET /api/organisateur/notifications/nonlu-count
    @GetMapping("/nonlu-count")
    public ResponseEntity<UnreadCountDTO> getUnreadCount() {
        long count = notifService.countUnread(securityUtils.getCurrentUserId());
        return ResponseEntity.ok(new UnreadCountDTO(count));
    }

    // PATCH /api/admin/notifications/{id}/lu
    @PatchMapping("/{id}/lu")
    public ResponseEntity<Void> markRead(@PathVariable Long id) {
        notifService.markRead(id);
        return ResponseEntity.ok().build();
    }

    // PATCH /api/admin/notifications/lit-tout
    @PatchMapping("/lire-tout")
    public ResponseEntity<Void> markAllRead() {
        notifService.markAllRead();
        return ResponseEntity.ok().build();
    }
}