package com.ouadia.rovista1.repositories;

import com.ouadia.rovista1.entities.Admin;
import com.ouadia.rovista1.entities.Client;
import com.ouadia.rovista1.entities.Reservation;
import com.ouadia.rovista1.entities.VisiteurInvite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ReservationRepository extends JpaRepository<Reservation,Long> {
public List<Reservation> findByClient(Client client);
public List<Reservation> findByVisiteurInvite(VisiteurInvite visiteurInvite);
public boolean existsByClient(Client client);
public boolean existsByVisiteurInvite(VisiteurInvite visiteurInvite);
}
