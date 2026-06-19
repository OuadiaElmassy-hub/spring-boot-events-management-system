package com.pfe.backend.dtos.notification;

import com.pfe.backend.entities.enums.TypeMessage;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
@Data
@AllArgsConstructor
@Builder
public class NotificationRequestDto {
    private String content;
    private LocalDateTime dateEnvoi;
    private TypeMessage typeMessage;
    private Long destinataireId;
}
