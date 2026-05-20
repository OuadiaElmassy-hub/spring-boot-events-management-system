package com.ouadia.rovista1.repositories;

import com.ouadia.rovista1.entities.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UtilisateurRepository extends JpaRepository<Utilisateur,Long> {
}
