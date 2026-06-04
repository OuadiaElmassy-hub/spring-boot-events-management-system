package com.ouadia.rovista1.mappers;

import com.ouadia.rovista1.dtos.paiement.PaiementRequestDto;
import com.ouadia.rovista1.dtos.paiement.PaiementResponseDto;
import com.ouadia.rovista1.entities.Paiement;
import com.ouadia.rovista1.entities.Paiement;
import com.ouadia.rovista1.exceptions.ReservationNotFoundException;
import com.ouadia.rovista1.services.implementations.ReservationServiceImpl;

public class PaiementMapper {

    ReservationServiceImpl reservationService;

    public Paiement mappingPaiementDtoRequestToPaiement(PaiementRequestDto dto) throws ReservationNotFoundException {
        return Paiement.builder()
                .montant(dto.getMontant())
                .datePaiement(dto.getDatePaiement())
                .statut(dto.getStatut())
                .methodePaiement(dto.getMethodePaiement())
                .reservation(reservationService.getReservationEntityById(dto.getReservationId()))
                .build();
    }
    public static PaiementResponseDto mappingPaiementToPaiementDtoResponse(Paiement e){
        return PaiementResponseDto.builder()
                .montant(e.getMontant())
                .datePaiement(e.getDatePaiement())
                .statut(e.getStatut())
                .methodePaiement(e.getMethodePaiement())
                .reservationId(e.getReservation().getId())
                .build();
    }
}
