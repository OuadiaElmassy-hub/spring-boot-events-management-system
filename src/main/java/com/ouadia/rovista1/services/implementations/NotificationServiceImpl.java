package com.ouadia.rovista1.services.implementations;

import com.ouadia.rovista1.entities.Notification;
import com.ouadia.rovista1.repositories.NotificationRepository;
import com.ouadia.rovista1.services.interfaces.INotificationService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class NotificationServiceImpl implements INotificationService {

    private NotificationRepository repository;


    @Override
    public Notification addNotification(Notification notification) {
        return repository.save(notification);
    }

    @Override
    public Notification editNotification(Notification notification) {
        return repository.save(notification);
    }

    @Override
    public Notification getNotificationById(Long id) {
        return repository.findById(id).orElseThrow();
    }

    @Override
    public List<Notification> getAllNotifications() {
        return repository.findAll();
    }

    @Override
    public void deleteNotificationById(Long id) {
        repository.deleteById(id);
    }
}
