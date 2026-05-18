package com.ouadia.rovista1.repositories;

import com.ouadia.rovista1.entities.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
}
