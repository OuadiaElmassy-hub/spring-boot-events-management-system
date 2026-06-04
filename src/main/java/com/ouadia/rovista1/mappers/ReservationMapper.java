package com.ouadia.rovista1.mappers;



import com.ouadia.rovista1.dtos.reservation.ReservationRequestDto;
import com.ouadia.rovista1.dtos.reservation.ReservationResponseDto;
import com.ouadia.rovista1.entities.Billet;
import com.ouadia.rovista1.entities.Reservation;
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
