package com.ouadia.rovista1.controllers.organisateur;

import com.ouadia.rovista1.dtos.admin.AdminNotificationDTO;
import com.ouadia.rovista1.dtos.admin.UnreadCountDTO;
import com.ouadia.rovista1.dtos.organisateur.OrganisateurNotificationDTO;
import com.ouadia.rovista1.security.SecurityUtils;
import com.ouadia.rovista1.services.organisateur.OrganizerNotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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

        return ResponseEntity.ok(notifService.getNotifications(page, size));
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