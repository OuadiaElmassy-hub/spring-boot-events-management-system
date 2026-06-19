package com.pfe.backend.dtos.reservation;

import com.pfe.backend.dtos.billet.BilletResponseDto;
import com.pfe.backend.entities.*;
import com.pfe.backend.entities.enums.StatutReservation;
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

    private Long id;
    private LocalDateTime dateReservation;
    private int nombrePlaces;
    private StatutReservation statut;
    private BigDecimal montant;
    private Long evenementId;
    private Long visiteurId;
    private Long clientId;
    private List<BilletResponseDto> billets;
}
