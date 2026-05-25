package com.ouadia.rovista1.services.interfaces;


import com.ouadia.rovista1.dtos.NotificationDto;
import com.ouadia.rovista1.dtos.UtilisateurDto;
import com.ouadia.rovista1.entities.Notification;
import com.ouadia.rovista1.entities.Utilisateur;
import com.ouadia.rovista1.exceptions.NotificationNotFoundException;
import com.ouadia.rovista1.exceptions.UserNotFoundException;

import java.util.List;
import java.util.Map;

public interface INotificationService {
    public NotificationDto addNotification(NotificationDto notificationDto);
    public NotificationDto editNotification(NotificationDto notificationDto ,Long idRech);
    public NotificationDto editNotificationMap(Long idReche , Map<String,Object> map);
    public NotificationDto getNotificationById(Long id)throws NotificationNotFoundException;
    public List<NotificationDto> getNotificationsByUtilisateur(Utilisateur utilisateur)throws NotificationNotFoundException, UserNotFoundException;
    public List<NotificationDto> getAllNotifications();
    public void deleteNotificationById(Long id);
    public void deleteAllByIds(Long ... ids);
}
