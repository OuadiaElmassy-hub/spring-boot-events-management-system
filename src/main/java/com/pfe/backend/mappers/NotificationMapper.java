package com.pfe.backend.mappers;



import com.pfe.backend.dtos.notification.NotificationRequestDto;
import com.pfe.backend.dtos.notification.NotificationResponseDto;
import com.pfe.backend.entities.Notification;

import com.pfe.backend.exceptions.UserNotFoundException;
import com.pfe.backend.repositories.UtilisateurRepository;
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
