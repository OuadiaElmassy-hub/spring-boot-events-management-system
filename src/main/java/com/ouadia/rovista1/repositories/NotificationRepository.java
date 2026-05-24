package com.ouadia.rovista1.repositories;

import com.ouadia.rovista1.entities.Admin;
import com.ouadia.rovista1.entities.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface NotificationRepository extends JpaRepository<Notification,Long> {

}
