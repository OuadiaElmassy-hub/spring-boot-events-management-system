package com.pfe.backend.repositories;

import com.pfe.backend.entities.Organisateur;
import jakarta.validation.constraints.Email;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrganisateurRepository extends JpaRepository<Organisateur,Long> {

    Optional<Organisateur> findByNumRegistre(Long num);
    Optional<Organisateur> findByEmail(String email);
    Page<Organisateur> findAllByOrderByDateValidationDesc(Pageable pageable);

    @Query("""
        SELECT op FROM Organisateur op
        WHERE (:search IS NULL OR
               LOWER(op.nom) LIKE LOWER(CONCAT('%', :search, '%')) OR
               LOWER(op.email) LIKE LOWER(CONCAT('%', :search, '%')))
        AND   (:verified IS NULL OR op.verified = :verified)
        ORDER BY op.verified ASC
    """)
    Page<Organisateur> search(
            @Param("search")   String search,
            @Param("verified") Boolean verified,
            Pageable pageable
    );

    boolean existsByEmail(@Email String email);
}
