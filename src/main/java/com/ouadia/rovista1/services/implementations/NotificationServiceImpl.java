package com.ouadia.rovista1.services.implementations;

import com.ouadia.rovista1.dtos.notification.NotificationRequestDto;
import com.ouadia.rovista1.dtos.notification.NotificationResponseDto;
import com.ouadia.rovista1.entities.*;
import com.ouadia.rovista1.entities.Notification;
import com.ouadia.rovista1.entities.enums.TypeMessage;
import com.ouadia.rovista1.exceptions.NotificationNotFoundException;
import com.ouadia.rovista1.exceptions.UserNotFoundException;
import com.ouadia.rovista1.mappers.NotificationMapper;
import com.ouadia.rovista1.repositories.NotificationRepository;
import com.ouadia.rovista1.services.interfaces.INotificationService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
@Transactional
@AllArgsConstructor
public class NotificationServiceImpl implements INotificationService {

    private NotificationRepository repository;
    private NotificationMapper notificationMapper;


    @Override
    public NotificationResponseDto addNotification(NotificationRequestDto notificationDto) throws UserNotFoundException {
        Notification notification= notificationMapper.mappingNotificationDtoRequestToNotification(notificationDto);
        return notificationMapper.mappingNotificationToNotificationDtoResponse(repository.save(notification));
    }

    @Override
    public NotificationResponseDto editNotification(NotificationRequestDto notificationDto, Long idRech) throws UserNotFoundException {
        Notification notification= notificationMapper.mappingNotificationDtoRequestToNotification(notificationDto);
        if (notification== null) return null;
        else {
            Notification notification1 = repository.findById(idRech).get();
            if (notification == null) {
                return null;
            }
            notification1.setMessage(notification.getMessage());
            notification1.setCreatedAt(notification.getCreatedAt());
            notification1.setTypeMessage(notification.getTypeMessage());
            notification1.setDestinataire(notification.getDestinataire());
            return notificationMapper.mappingNotificationToNotificationDtoResponse (repository.save(notification1));
        }
    }

    @Override
    public NotificationResponseDto editNotificationMap(Long idReche, Map<String, Object> map) {
        if (map==null){return null;}
        Notification notification =repository.findById(idReche).get();
        if (notification == null) {
            return null;
        }
        if (map.containsKey("content")) {
            notification.setMessage((String) map.get("content"));
        }
        if (map.containsKey("dateEnvoi")) {
            notification.setCreatedAt((LocalDateTime) map.get("dateEnvoi"));
        }
        if (map.containsKey("typeMessage")) {
            notification.setTypeMessage(TypeMessage.valueOf (map.get("typeMessage").toString()));
        }
        if (map.containsKey("destinataire")) {
            notification.setDestinataire((Utilisateur) map.get("destinataire"));
        }
        return notificationMapper.mappingNotificationToNotificationDtoResponse(repository.save(notification));
    }

    @Override
    public NotificationResponseDto getNotificationById(Long id)throws NotificationNotFoundException {
        Notification notification = repository.findById(id).orElseThrow(() -> new NotificationNotFoundException("notification not found"));
        return notificationMapper.mappingNotificationToNotificationDtoResponse(notification);
    }

    @Override
    public List<NotificationResponseDto> getNotificationsByUtilisateur(Utilisateur utilisateur) throws NotificationNotFoundException, UserNotFoundException {
     return repository.findByDestinataire(utilisateur).stream().map(notification -> notificationMapper.mappingNotificationToNotificationDtoResponse(notification)).toList();
    }

    @Override
    public List<NotificationResponseDto> getAllNotifications() {
        return repository.findAll().stream().map(notification -> notificationMapper.mappingNotificationToNotificationDtoResponse(notification)).toList();

    }

    @Override
    public void deleteNotificationById(Long id) {
        repository.deleteById(id);
    }

    @Override
    public void deleteAllByIds(Long... ids) {
        for (Long id :ids){
            deleteNotificationById(id);
        }
    }
}
