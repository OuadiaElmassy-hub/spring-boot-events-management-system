package com.ouadia.rovista1.dtos.promotion;

import com.ouadia.rovista1.entities.Client;
import com.ouadia.rovista1.entities.Evenement;
import com.ouadia.rovista1.entities.Organisateur;
import com.ouadia.rovista1.entities.enums.TypePromotion;

import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class PromotionResponseDto {
    private Long id;
    private String titre;
    private LocalDateTime dateDebut;
    private LocalDateTime dateFin;
    private TypePromotion type;
    private boolean estApprove;
    private Long organisateurId;
    private List<Long> clientsId;
    private List<Long> evenementsId;
}
