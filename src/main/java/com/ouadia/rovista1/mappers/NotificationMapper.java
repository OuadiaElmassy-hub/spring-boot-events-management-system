package com.ouadia.rovista1.mappers;



import com.ouadia.rovista1.dtos.notification.NotificationRequestDto;
import com.ouadia.rovista1.dtos.notification.NotificationResponseDto;
import com.ouadia.rovista1.entities.Notification;

import com.ouadia.rovista1.exceptions.UserNotFoundException;
import com.ouadia.rovista1.repositories.UtilisateurRepository;


public class NotificationMapper {
UtilisateurRepository utilisateurRepository;

    public Notification mappingNotificationDtoRequestToNotification(NotificationRequestDto dto) throws UserNotFoundException {
        return Notification.builder()
                .content(dto.getContent())
                .dateEnvoi(dto.getDateEnvoi())
                .typeMessage(dto.getTypeMessage())
                .destinataire(utilisateurRepository.findById(dto.getDestinataireId()).get())
                .build();
    }
    public static NotificationResponseDto mappingNotificationToNotificationDtoResponse(Notification e){
        return  NotificationResponseDto.builder()
                .content(e.getContent())
                .dateEnvoi(e.getDateEnvoi())
                .typeMessage(e.getTypeMessage())
                .destinataireId(e.getDestinataire().getId())
                .build();
    }
}
