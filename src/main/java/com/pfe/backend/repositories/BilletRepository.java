package com.pfe.backend.repositories;

import com.pfe.backend.entities.Billet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface BilletRepository extends JpaRepository<Billet,Long> {
    List<Billet> findByReservationId(Long reservationId);
}
