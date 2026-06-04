package com.ouadia.rovista1.dtos.utilisateur;

import com.ouadia.rovista1.entities.Notification;
import com.ouadia.rovista1.entities.Role;
import com.ouadia.rovista1.entities.enums.StatutCompte;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class UtilisateurResponseDto {
    private Long id;
    private String email;
    private String username;
    private StatutCompte statutCompte;
    private String phone;
    private String adresse;
    private List<Long> notificationsId;
    private List<Integer> rolesId ;
}
