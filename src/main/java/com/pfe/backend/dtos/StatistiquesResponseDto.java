package com.pfe.backend.dtos;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class StatistiquesResponseDto {

    private String nom;
    private String prenom;
    private String email;
    private long   totalBookings;
    private long   eventsAttended;   // réservations confirmées + date passée
    private long   totalFavorites;    // nombre d'événements ajoutés en favoris
}
