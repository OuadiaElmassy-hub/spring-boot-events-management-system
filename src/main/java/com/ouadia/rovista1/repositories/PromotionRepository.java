package com.ouadia.rovista1.repositories;

import com.ouadia.rovista1.entities.Admin;
import com.ouadia.rovista1.entities.Promotion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface PromotionRepository extends JpaRepository<Promotion,Long> {

    Page<Promotion> findByOrganisateurId( Long orgId, Pageable pageable);

    Optional<Promotion> findByCodeIgnoreCase(String code);
    boolean existsByCodeIgnoreCaseAndIdNot(String code, Long id);
    boolean existsByCodeIgnoreCase(String code);

    Optional<Promotion> findByIdAndOrganisateurId(Long promId, Long orgId);
}
