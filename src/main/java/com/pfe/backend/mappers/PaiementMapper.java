package com.pfe.backend.mappers;

import com.pfe.backend.dtos.paiement.PaiementRequestDto;
import com.pfe.backend.dtos.paiement.PaiementResponseDto;
import com.pfe.backend.entities.Paiement;
import com.pfe.backend.exceptions.ReservationNotFoundException;
import com.pfe.backend.services.implementations.ReservationServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
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
    public PaiementResponseDto mappingPaiementToPaiementDtoResponse(Paiement e){
        return PaiementResponseDto.builder()
                .montant(e.getMontant())
                .datePaiement(e.getDatePaiement())
                .statut(e.getStatut())
                .methodePaiement(e.getMethodePaiement())
                .reservationId(e.getReservation().getId())
                .build();
    }
}
