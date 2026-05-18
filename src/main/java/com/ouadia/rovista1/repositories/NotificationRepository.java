package com.ouadia.rovista1.repositories;

import com.ouadia.rovista1.entities.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification,Long> {
}
