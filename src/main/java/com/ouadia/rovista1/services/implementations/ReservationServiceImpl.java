package com.ouadia.rovista1.services.implementations;

import com.ouadia.rovista1.entities.Reservation;
import com.ouadia.rovista1.repositories.ReservationRepository;
import com.ouadia.rovista1.services.interfaces.IReservationService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class ReservationServiceImpl implements IReservationService {

    private ReservationRepository repository;


    @Override
    public Reservation addReservation(Reservation reservation) {
        return repository.save(reservation);
    }

    @Override
    public Reservation editReservation(Reservation reservation) {
        return repository.save(reservation);
    }

    @Override
    public Reservation getReservationById(Long id) {
        return repository.findById(id).orElseThrow();
    }

    @Override
    public List<Reservation> getAllReservations() {
        return repository.findAll();
    }

    @Override
    public void deleteReservationById(Long id) {
        repository.deleteById(id);
    }
}
