package com.ouadia.rovista1.repositories;

import com.ouadia.rovista1.entities.VisiteurInvite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface VisiteurRepository extends JpaRepository<VisiteurInvite,Long> {
}
