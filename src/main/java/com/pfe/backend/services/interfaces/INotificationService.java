package com.pfe.backend.services.interfaces;


import com.pfe.backend.dtos.notification.NotificationRequestDto;
import com.pfe.backend.dtos.notification.NotificationResponseDto;
import com.pfe.backend.entities.Utilisateur;
import com.pfe.backend.exceptions.NotificationNotFoundException;
import com.pfe.backend.exceptions.UserNotFoundException;

import java.util.List;
import java.util.Map;

public interface INotificationService {
    public NotificationResponseDto addNotification(NotificationRequestDto notificationDto) throws UserNotFoundException;
    public NotificationResponseDto editNotification(NotificationRequestDto notificationDto ,Long idRech) throws UserNotFoundException;
    public NotificationResponseDto editNotificationMap(Long idReche , Map<String,Object> map);
    public NotificationResponseDto getNotificationById(Long id)throws NotificationNotFoundException;
    public List<NotificationResponseDto> getNotificationsByUtilisateur(Utilisateur utilisateur)throws NotificationNotFoundException, UserNotFoundException;
    public List<NotificationResponseDto> getAllNotifications();
    public void deleteNotificationById(Long id);
    public void deleteAllByIds(Long ... ids);
}
