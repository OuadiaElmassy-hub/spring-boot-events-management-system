package com.pfe.backend.dtos.organisateur;
import com.pfe.backend.entities.enums.TypeMessage;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class OrganisateurNotificationDTO {
    private Long id;
    private String message;
    private TypeMessage type;
    private boolean read;
    private String createdAt;
}