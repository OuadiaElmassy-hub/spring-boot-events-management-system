package com.ouadia.rovista1.dtos.notification;

import com.ouadia.rovista1.entities.Utilisateur;
import com.ouadia.rovista1.entities.enums.TypeMessage;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
@Data
@AllArgsConstructor
@Builder
public class NotificationResponseDto {
    private Long id;
    private String content;
    private LocalDateTime dateEnvoi;
    private TypeMessage typeMessage;
    private Long destinataireId;
}
