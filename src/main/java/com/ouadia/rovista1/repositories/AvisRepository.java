package com.ouadia.rovista1.repositories;

import com.ouadia.rovista1.entities.Avis;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface AvisRepository extends JpaRepository<Avis,Long> {
}
