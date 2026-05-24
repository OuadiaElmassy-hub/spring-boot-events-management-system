package com.ouadia.rovista1.Mapper;

import com.ouadia.rovista1.dtos.NotificationDto;
import com.ouadia.rovista1.entities.Notification;

public class NotificationMapper {
    public static NotificationDto mapToNotificationDto(Notification notification){

        return new NotificationDto(
                notification.getId(),
                notification.getContent(),
                notification.getDateEnvoi(),
                notification.getTypeMessage()
        );
    }

    public static Notification mapToNotification(NotificationDto dto){

        return new Notification(
                dto.getId(),
                dto.getContent(),
                dto.getDateEnvoi(),
                dto.getTypeMessage(),
                null
        );
    }
}
