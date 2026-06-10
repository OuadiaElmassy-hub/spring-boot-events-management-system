package com.ouadia.rovista1.controllers.organisateur;

import com.ouadia.rovista1.dtos.admin.UnreadCountDTO;
import com.ouadia.rovista1.security.SecurityUtils;
import com.ouadia.rovista1.services.organisateur.OrganizerNotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/organisateur/notifications")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ORGANISATEUR')")
public class OrganizerNotificationController {

    private final OrganizerNotificationService notifService;
    private final SecurityUtils                securityUtils;

    // GET /api/organisateur/notifications/nonlu-count
    @GetMapping("/nonlu-count")
    public ResponseEntity<UnreadCountDTO> getUnreadCount() {
        long count = notifService.countUnread(securityUtils.getCurrentUserId());
        return ResponseEntity.ok(new UnreadCountDTO(count));
    }
}