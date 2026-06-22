package com.pfe.backend.controllers.admin;

import com.pfe.backend.dtos.admin.AdminNotificationDTO;
import com.pfe.backend.dtos.admin.UnreadCountDTO;
import com.pfe.backend.services.admin.AdminNotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/notifications")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class AdminNotificationController {

    private final AdminNotificationService notifService;

    // GET /api/admin/notifications?page=0&size=15&sort=createdAt,desc
    @GetMapping
    public ResponseEntity<Page<AdminNotificationDTO>> getNotifications(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        return ResponseEntity.ok(notifService.getNotifications(page, size));
    }

    // GET /api/admin/notifications/nonlu-count
    @GetMapping("/nonlu-count")
    public ResponseEntity<UnreadCountDTO> getUnreadCount() {
        return ResponseEntity.ok(new UnreadCountDTO(notifService.countUnread()));
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