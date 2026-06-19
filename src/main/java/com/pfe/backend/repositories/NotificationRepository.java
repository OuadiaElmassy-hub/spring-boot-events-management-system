package com.pfe.backend.repositories;

import com.pfe.backend.entities.Notification;
import com.pfe.backend.entities.Utilisateur;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface NotificationRepository extends JpaRepository<Notification,Long> {
    List<Notification> findByDestinataire(Utilisateur destinataire);

    // Notifications admin (user null = globales)
    Page<Notification> findByDestinataireIsNullOrderByCreatedAtDesc(Pageable pageable);

    long countByDestinataireIsNullAndEstLuFalse();

    @Modifying
    @Query("UPDATE Notification n SET n.estLu = true WHERE n.destinataire IS NULL")
    void markAllAdminRead();

    @Modifying
    @Query("UPDATE Notification n SET n.estLu = true WHERE n.id = :id AND n.destinataire IS NULL")
    void markOneRead(@Param("id") Long id);

    long countByDestinataireIdAndEstLuFalse(Long userId);

    @Query("SELECT COUNT(n) FROM Notification n WHERE n.destinataire IS NULL AND n.estLu = false")
    long countAdminUnread();

    /** Notifications d'un utilisateur spécifique */
    Page<Notification> findByDestinataireIdOrderByCreatedAtDesc(Long userId, Pageable pageable);

    @Query("SELECT COUNT(n) FROM Notification n WHERE n.destinataire.id = :uid AND n.estLu = false")
    long countUnreadByUser(@Param("uid") Long uid);

    @Modifying
    @Query("UPDATE Notification n SET n.estLu = true WHERE n.destinataire.id = :uid")
    void markAllUserRead(@Param("uid") Long uid);
}
