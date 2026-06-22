package com.pfe.backend.repositories;

import com.pfe.backend.entities.Paiement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PaiementRepository extends JpaRepository<Paiement, Integer> {

}
