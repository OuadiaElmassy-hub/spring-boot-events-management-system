package com.pfe.backend.controllers;




import com.pfe.backend.dtos.notification.NotificationRequestDto;
import com.pfe.backend.dtos.notification.NotificationResponseDto;
import com.pfe.backend.exceptions.NotificationNotFoundException;
import com.pfe.backend.exceptions.ReservationNotFoundException;
import com.pfe.backend.exceptions.UserNotFoundException;
import com.pfe.backend.services.implementations.NotificationServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/notification")
public class NotificationController {
    final NotificationServiceImpl notificationService;

    @PostMapping
    public ResponseEntity<NotificationResponseDto> createNotification(@RequestBody NotificationRequestDto notification) throws NotificationNotFoundException, ReservationNotFoundException, UserNotFoundException {

        return new ResponseEntity<>((notificationService.addNotification(notification)), HttpStatus.CREATED);
    }


    // racherche notification
    @GetMapping("{id}")
    public ResponseEntity<NotificationResponseDto> RechercheNotification(@PathVariable("id") Long id) throws NotificationNotFoundException {
        return ResponseEntity.ok(notificationService.getNotificationById(id));
    }

    @GetMapping
    public ResponseEntity<List<NotificationResponseDto>> GetAllNotification() throws NotificationNotFoundException {
        return ResponseEntity.ok(notificationService.getAllNotifications());
    }

    //update notification
    @PutMapping("{id}")
    public ResponseEntity<NotificationResponseDto> UpdateNotification(@PathVariable("id") Long id,
                                                                      @RequestBody NotificationRequestDto notification
    ) throws NotificationNotFoundException, NotificationNotFoundException, ReservationNotFoundException, UserNotFoundException {
        return ResponseEntity.ok(notificationService.editNotification(notification, id));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> DeleteNotification(@PathVariable("id") Long id) {
        notificationService.deleteNotificationById(id);
        return ResponseEntity.ok("notification deleted successfully ! ✅");
    }
}