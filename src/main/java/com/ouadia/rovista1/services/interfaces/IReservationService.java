package com.ouadia.rovista1.services.interfaces;


import com.ouadia.rovista1.dtos.ReservationDto;
import com.ouadia.rovista1.entities.Client;
import com.ouadia.rovista1.entities.Reservation;
import com.ouadia.rovista1.exceptions.ReservationNotFoundException;

import java.util.List;
import java.util.Map;

public interface IReservationService {
    public ReservationDto addReservation(ReservationDto reservationDto);
    public ReservationDto editReservation(ReservationDto reservationDto ,  Long id);
    public ReservationDto editReservationMap(  Long id , Map<String,Object> map);
    public ReservationDto getReservationById(  Long id )throws ReservationNotFoundException;
    public List<ReservationDto> getReservationByClient(Client client)throws ReservationNotFoundException;
    public List<ReservationDto> getAllReservations();
    public void deleteReservationById( Long id);
    public void deleteAllByIds(Long ... ids);
}
