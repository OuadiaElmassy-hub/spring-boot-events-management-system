package com.ouadia.rovista1.Mapper;

import com.ouadia.rovista1.dtos.ReservationDto;
import com.ouadia.rovista1.entities.Reservation;

public class ReservationMapper {
    public static ReservationDto mapToReservationDto(Reservation reservation) {

        return new ReservationDto(
                reservation.getId(),
                reservation.getDateReservation(),
                reservation.getNombrePlaces(),
                reservation.getStatut(),
                reservation.getMontant()
        );
    }

    public static Reservation mapToReservation(ReservationDto dto) {

        return new Reservation(
                dto.getId(),
                dto.getDateReservation(),
                dto.getNombrePlaces(),
                dto.getStatut(),
                dto.getMontant(),
                null,
                null,
                null,
                null,
                null
        );
    }
}