package com.ouadia.rovista1.services.implementations;

import com.ouadia.rovista1.Mapper.*;
import com.ouadia.rovista1.Mapper.NotificationMapper;
import com.ouadia.rovista1.Mapper.NotificationMapper;
import com.ouadia.rovista1.Mapper.NotificationMapper;
import com.ouadia.rovista1.dtos.NotificationDto;
import com.ouadia.rovista1.dtos.UtilisateurDto;
import com.ouadia.rovista1.entities.*;
import com.ouadia.rovista1.entities.Notification;
import com.ouadia.rovista1.entities.Notification;
import com.ouadia.rovista1.entities.enums.TypeMessage;
import com.ouadia.rovista1.entities.enums.TypePhoto;
import com.ouadia.rovista1.exceptions.NotificationNotFoundException;
import com.ouadia.rovista1.exceptions.UserNotFoundException;
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


    @Override
    public NotificationDto addNotification(NotificationDto notificationDto) {
        Notification notification= NotificationMapper.mapToNotification(notificationDto);
        return NotificationMapper.mapToNotificationDto(repository.save(notification));
    }

    @Override
    public NotificationDto editNotification(NotificationDto notificationDto, Long idRech) {
        Notification notification= NotificationMapper.mapToNotification(notificationDto);
        if (notification== null) return null;
        else {
            Notification notification1 = repository.findById(idRech).get();
            if (notification == null) {
                return null;
            }
            notification1.setContent(notification.getContent());
            notification1.setDateEnvoi(notification.getDateEnvoi());
            notification1.setTypeMessage(notification.getTypeMessage());
            notification1.setDestinataire(notification.getDestinataire());
            return NotificationMapper.mapToNotificationDto (repository.save(notification1));
        }
    }

    @Override
    public NotificationDto editNotificationMap(Long idReche, Map<String, Object> map) {
        if (map==null){return null;}
        Notification notification =repository.findById(idReche).get();
        if (notification == null) {
            return null;
        }
        if (map.containsKey("content")) {
            notification.setContent((String) map.get("content"));
        }
        if (map.containsKey("dateEnvoi")) {
            notification.setDateEnvoi((LocalDateTime) map.get("dateEnvoi"));
        }
        if (map.containsKey("typeMessage")) {
            notification.setTypeMessage(TypeMessage.valueOf (map.get("typeMessage").toString()));
        }
        if (map.containsKey("destinataire")) {
            notification.setDestinataire((Utilisateur) map.get("destinataire"));
        }
        return NotificationMapper.mapToNotificationDto(repository.save(notification));
    }

    @Override
    public NotificationDto getNotificationById(Long id)throws NotificationNotFoundException {
        Notification notification = repository.findById(id).orElseThrow(() -> new NotificationNotFoundException("notification not found"));
        return NotificationMapper.mapToNotificationDto(notification);
    }

    @Override
    public List<NotificationDto> getNotificationsByUtilisateur(Utilisateur utilisateur) throws NotificationNotFoundException, UserNotFoundException {
     return repository.findByUtilisateur(utilisateur).stream().map(notification -> NotificationMapper.mapToNotificationDto(notification)).toList();
    }

    @Override
    public List<NotificationDto> getAllNotifications() {
        return repository.findAll().stream().map(notification -> NotificationMapper.mapToNotificationDto(notification)).toList();

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
