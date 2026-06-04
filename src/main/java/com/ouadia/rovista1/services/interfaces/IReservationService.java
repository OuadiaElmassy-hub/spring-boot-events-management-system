package com.ouadia.rovista1.services.interfaces;


import com.ouadia.rovista1.dtos.reservation.ReservationRequestDto;
import com.ouadia.rovista1.dtos.reservation.ReservationResponseDto;
import com.ouadia.rovista1.entities.Client;
import com.ouadia.rovista1.entities.Reservation;
import com.ouadia.rovista1.entities.VisiteurInvite;
import com.ouadia.rovista1.exceptions.ReservationNotFoundException;

import java.util.List;
import java.util.Map;

public interface IReservationService {
    public ReservationResponseDto addReservation(ReservationRequestDto reservationDto);
    public ReservationResponseDto editReservation(ReservationRequestDto reservationDto ,  Long id);
    public ReservationResponseDto editReservationMap(  Long id , Map<String,Object> map);
    public ReservationResponseDto getReservationById(  Long id )throws ReservationNotFoundException;
    public Reservation getReservationEntityById(Long id)throws ReservationNotFoundException;
    public List<ReservationResponseDto> getReservationByClient(Client client)throws ReservationNotFoundException;
    public List<ReservationResponseDto> getReservationByVisiteur(VisiteurInvite visiteurInvite)throws ReservationNotFoundException;
    public List<ReservationResponseDto> getAllReservations();
    public void deleteReservationById( Long id);
    public void deleteAllByIds(Long ... ids);
}
