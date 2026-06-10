package com.ouadia.rovista1.services.organisateur;

import com.ouadia.rovista1.repositories.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrganizerNotificationService {

    private final NotificationRepository notifRepo;

    public long countUnread(Long userId) {
        return notifRepo.countByDestinataireIdAndEstLuFalse(userId);
    }
}