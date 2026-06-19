package com.pfe.backend.dtos.utilisateur;

import com.pfe.backend.entities.enums.StatutCompte;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

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
