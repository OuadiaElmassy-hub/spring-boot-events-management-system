package com.ouadia.rovista1.dtos.reservation;

import com.ouadia.rovista1.entities.Billet;
import com.ouadia.rovista1.entities.enums.StatutPaiement;
import com.ouadia.rovista1.entities.enums.StatutReservation;
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
