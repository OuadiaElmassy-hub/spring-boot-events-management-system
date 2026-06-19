package com.pfe.backend.controllers.client;

import com.pfe.backend.dtos.admin.UnreadCountDTO;
import com.pfe.backend.security.SecurityUtils;
import com.pfe.backend.services.client.ClientNotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/client/notifications")
@RequiredArgsConstructor
@PreAuthorize("hasRole('CLIENT')")
public class ClientNotificationController {

    private final ClientNotificationService notifService;
    private final SecurityUtils                securityUtils;

    // GET /api/client/notifications/nonlu-count
    @GetMapping("/nonlu-count")
    public ResponseEntity<UnreadCountDTO> getUnreadCount() {
        long count = notifService.countUnread(securityUtils.getCurrentUserId());
        return ResponseEntity.ok(new UnreadCountDTO(count));
    }
}