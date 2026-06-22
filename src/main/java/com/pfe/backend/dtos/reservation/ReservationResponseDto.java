package com.pfe.backend.dtos.reservation;

import com.pfe.backend.entities.enums.StatutPaiement;
import com.pfe.backend.entities.enums.StatutReservation;
import lombok.Builder;


import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;


@Builder
public class ReservationResponseDto {
    private Long id;
    private LocalDateTime dateReservation;
    private int nombrePlaces;
    private StatutReservation statut;
    private BigDecimal montant;
    private List<Long> billetsId;
    private StatutPaiement paiement;
    private String  Nomevenement;
    private String NomvisiteurInvite;
    private String Nomclient;

}
