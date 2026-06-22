package com.pfe.backend.services.interfaces;


import com.pfe.backend.dtos.reservation.HistoriqueReservationDto;
import com.pfe.backend.dtos.reservation.ReservationRequestDto;
import com.pfe.backend.dtos.reservation.ReservationResponseDto;
import com.pfe.backend.entities.Client;
import com.pfe.backend.entities.Reservation;
import com.pfe.backend.entities.VisiteurInvite;
import com.pfe.backend.entities.enums.StatutReservation;
import com.pfe.backend.exceptions.ReservationNotFoundException;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;

public interface IReservationService {
    public ReservationResponseDto addReservation(ReservationRequestDto reservationDto);
    public ReservationResponseDto editReservation(ReservationRequestDto reservationDto ,  Long id);
    public ReservationResponseDto editReservationMap(  Long id , Map<String,Object> map);
    public ReservationResponseDto getReservationById(  Long id )throws ReservationNotFoundException;
    //Page<ReservationResponseDto> getReservationsByClientId(Long clientId )throws ClientNotFoundException;
    public Reservation getReservationEntityById(Long id)throws ReservationNotFoundException;
    public List<ReservationResponseDto> getReservationByClient(Client client)throws ReservationNotFoundException;
    public List<ReservationResponseDto> getReservationByVisiteur(VisiteurInvite visiteurInvite)throws ReservationNotFoundException;
    public List<ReservationResponseDto> getAllReservations();
    public void deleteReservationById( Long id);
    public void deleteAllByIds(Long ... ids);

    Page<HistoriqueReservationDto> getBookings(
            Long clientId, String statut, int page, int size);

    byte[] generateTicketPdf(Long reservationId, Long clientId);

    HistoriqueReservationDto toDTO(Reservation r);

    StatutReservation mapStatut(String s);

    byte[] buildPdf(Reservation r);
}
