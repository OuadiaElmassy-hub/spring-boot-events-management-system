package com.pfe.backend.services.admin;

import com.pfe.backend.dtos.admin.AdminNotificationDTO;
import com.pfe.backend.entities.Notification;
import com.pfe.backend.repositories.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class AdminNotificationService {

    private final NotificationRepository notifRepo;

    public Page<AdminNotificationDTO> getNotifications(int page, int size) {
        Pageable pageable = PageRequest.of(page,size);
        return notifRepo
            .findByDestinataireIsNullOrderByCreatedAtDesc(pageable)
            .map(this::toDTO);
    }

    public long countUnread() {
        return notifRepo.countByDestinataireIsNullAndEstLuFalse();
    }

    @Transactional
    public void markRead(Long id) {
        notifRepo.markOneRead(id);
    }

    @Transactional
    public void markAllRead() {
        notifRepo.markAllAdminRead();
    }

    private AdminNotificationDTO toDTO(Notification n) {
        return new AdminNotificationDTO(
            n.getId(),
            n.getMessage(),
            n.getTypeMessage(),
            n.isEstLu(),
            n.getCreatedAt() != null ? n.getCreatedAt().toString() : null
        );
    }
}