package com.ouadia.rovista1.repositories;

import com.ouadia.rovista1.entities.Organisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrganisateurRepository extends JpaRepository<Organisateur,Long> {
    Optional<Organisateur> findByNumRegistre(Long num);
}
