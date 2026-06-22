package com.pfe.backend.repositories;

import com.pfe.backend.entities.VisiteurInvite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface VisiteurRepository extends JpaRepository<VisiteurInvite,Long> {
}
