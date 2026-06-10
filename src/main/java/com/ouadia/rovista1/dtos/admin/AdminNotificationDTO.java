package com.ouadia.rovista1.dtos.admin;
import com.ouadia.rovista1.entities.enums.TypeMessage;
import lombok.AllArgsConstructor;
import lombok.Builder;
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