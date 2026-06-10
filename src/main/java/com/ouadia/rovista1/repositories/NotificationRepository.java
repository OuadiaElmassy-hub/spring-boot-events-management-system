package com.ouadia.rovista1.repositories;

import com.ouadia.rovista1.entities.Notification;
import com.ouadia.rovista1.entities.Utilisateur;
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
}
