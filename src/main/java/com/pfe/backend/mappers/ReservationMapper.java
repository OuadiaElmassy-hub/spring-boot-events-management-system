package com.pfe.backend.mappers;



import com.pfe.backend.dtos.reservation.ReservationRequestDto;
import com.pfe.backend.dtos.reservation.ReservationResponseDto;
import com.pfe.backend.entities.Billet;
import com.pfe.backend.entities.Reservation;
import org.springframework.stereotype.Component;

@Component
public class ReservationMapper {

    public Reservation mappingReservationDtoRequestToReservation(ReservationRequestDto dto){
        return  Reservation.builder()
                .dateReservation(dto.getDateReservation())
                .nombrePlaces(dto.getNombrePlaces())
                .statut(dto.getStatut())
                .montant(dto.getMontant())
                .build();
    }
    public ReservationResponseDto mappingReservationToReservationDtoResponse(Reservation e){
        return ReservationResponseDto.builder()
                .dateReservation(e.getDateReservation())
                .nombrePlaces(e.getNombrePlaces())
                .statut(e.getStatut())
                .montant(e.getMontant())
                .billetsId(e.getBillets().stream().map(Billet::getId).toList())
                .paiement(e.getPaiement().getStatut())
                .Nomevenement(e.getEvenement().getTitre())
                .NomvisiteurInvite(e.getVisiteurInvite().getNom())
                .Nomclient(e.getClient().getNom())
                .build();
    }
}
