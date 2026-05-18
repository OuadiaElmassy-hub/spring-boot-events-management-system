package com.ouadia.rovista1.repositories;

import com.ouadia.rovista1.entities.Paiement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaiementRepository extends JpaRepository<Paiement, Long> {
}
