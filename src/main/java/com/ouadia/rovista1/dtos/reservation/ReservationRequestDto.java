package com.ouadia.rovista1.dtos.reservation;

import com.ouadia.rovista1.entities.*;
import com.ouadia.rovista1.entities.enums.StatutPaiement;
import com.ouadia.rovista1.entities.enums.StatutReservation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
@Data
@AllArgsConstructor
@Builder
public class ReservationRequestDto {

    private LocalDateTime dateReservation;
    private int nombrePlaces;
    private StatutReservation statut;
    private BigDecimal montant;
    private Long paiementId;
    private Long evenementId;
    private Long visiteurId;
    private Long clientId;
}
