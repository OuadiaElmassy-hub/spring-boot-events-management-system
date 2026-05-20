package com.ouadia.rovista1.services.interfaces;


import com.ouadia.rovista1.entities.Reservation;

import java.util.List;

public interface IReservationService {
    public Reservation addReservation(Reservation reservation);
    public Reservation editReservation(Reservation reservation);
    public Reservation getReservationById(Long id);
    public List<Reservation> getAllReservations();
    public void deleteReservationById(Long id);
}
