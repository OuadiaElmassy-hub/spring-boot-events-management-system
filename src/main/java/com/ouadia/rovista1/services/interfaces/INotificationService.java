package com.ouadia.rovista1.services.interfaces;


import com.ouadia.rovista1.entities.Notification;

import java.util.List;

public interface INotificationService {
    public Notification addNotification(Notification notification);
    public Notification editNotification(Notification notification);
    public Notification getNotificationById(Long id);
    public List<Notification> getAllNotifications();
    public void deleteNotificationById(Long id);
}
