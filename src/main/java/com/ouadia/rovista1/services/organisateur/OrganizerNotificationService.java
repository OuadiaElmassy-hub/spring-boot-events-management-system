package com.ouadia.rovista1.services.organisateur;

import com.ouadia.rovista1.dtos.admin.AdminNotificationDTO;
import com.ouadia.rovista1.dtos.organisateur.OrganisateurNotificationDTO;
import com.ouadia.rovista1.entities.Notification;
import com.ouadia.rovista1.repositories.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrganizerNotificationService {

    private final NotificationRepository notifRepo;


    public Page<OrganisateurNotificationDTO> getNotifications(int page, int size) {
        Pageable pageable = PageRequest.of(page,size);
        return notifRepo
                .findByDestinataireIsNullOrderByCreatedAtDesc(pageable)
                .map(this::toDTO);
    }

    public long countUnread(Long userId) {
        return notifRepo.countByDestinataireIdAndEstLuFalse(userId);
    }
    @Transactional
    public void markRead(Long id) {
        notifRepo.markOneRead(id);
    }

    @Transactional
    public void markAllRead() {
        notifRepo.markAllAdminRead();
    }

    private OrganisateurNotificationDTO toDTO(Notification n) {
        return new OrganisateurNotificationDTO(
                n.getId(),
                n.getMessage(),
                n.getTypeMessage(),
                n.isEstLu(),
                n.getCreatedAt() != null ? n.getCreatedAt().toString() : null
        );
    }
}