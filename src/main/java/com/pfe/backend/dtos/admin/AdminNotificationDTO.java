package com.pfe.backend.dtos.admin;
import com.pfe.backend.entities.enums.TypeMessage;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AdminNotificationDTO {
    private Long id;
    private String message;
    private TypeMessage type;
    private boolean read;
    private String createdAt;
}