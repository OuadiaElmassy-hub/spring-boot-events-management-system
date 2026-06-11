package com.ouadia.rovista1.mappers;



import com.ouadia.rovista1.dtos.notification.NotificationRequestDto;
import com.ouadia.rovista1.dtos.notification.NotificationResponseDto;
import com.ouadia.rovista1.entities.Notification;

import com.ouadia.rovista1.exceptions.UserNotFoundException;
import com.ouadia.rovista1.repositories.UtilisateurRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class NotificationMapper {
UtilisateurRepository utilisateurRepository;

    public Notification mappingNotificationDtoRequestToNotification(NotificationRequestDto dto) throws UserNotFoundException {
        return Notification.builder()
                .message(dto.getContent())
                .createdAt(dto.getDateEnvoi())
                .typeMessage(dto.getTypeMessage())
                .destinataire(utilisateurRepository.findById(dto.getDestinataireId()).get())
                .build();
    }
    public NotificationResponseDto mappingNotificationToNotificationDtoResponse(Notification e){
        return  NotificationResponseDto.builder()
                .content(e.getMessage())
                .dateEnvoi(e.getCreatedAt())
                .typeMessage(e.getTypeMessage())
                .destinataireId(e.getDestinataire().getId())
                .build();
    }
}
