package com.ouadia.rovista1.repositories;


import com.ouadia.rovista1.entities.Avis;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface AvisRepository extends JpaRepository<Avis,Long> {


    List<Avis> findByEvenementId(Long evenementId);
}
