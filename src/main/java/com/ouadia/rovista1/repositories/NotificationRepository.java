package com.ouadia.rovista1.repositories;

import com.ouadia.rovista1.entities.Admin;
import com.ouadia.rovista1.entities.Notification;
import com.ouadia.rovista1.entities.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface NotificationRepository extends JpaRepository<Notification,Long> {
public List<Notification> findByDestinataire(Utilisateur destinataire);
}
