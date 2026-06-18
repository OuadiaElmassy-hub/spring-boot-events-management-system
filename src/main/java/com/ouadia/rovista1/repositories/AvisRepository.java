package com.ouadia.rovista1.repositories;


import com.ouadia.rovista1.dtos.avis.AvisResponseDto;
import com.ouadia.rovista1.entities.Avis;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.List;


@Repository
public interface AvisRepository extends JpaRepository<Avis,Long> {

    //Optional<Page<Avis>> findAvisByEvenementId(Long id, Pageable pageable);
//    @Query("SELECT new com.ouadia.rovista1.dtos.avis.AvisResponseDto(a.id, a.note, a.comment, a.dateAvis, v.nom, c.prenom, c.avatar, a.evenement.id) " +
//            "FROM Avis a JOIN a.client c JOIN a.visiteur v " +
//            "WHERE a.evenement.id = :eventId")
    @Query("SELECT new com.ouadia.rovista1.dtos.avis.AvisResponseDto(" +
            "a.id, a.note, a.comment, a.dateAvis, " +
            "COALESCE(c.nom, v.nom), " +
            "COALESCE(c.prenom, v.prenom), " +
            " c.avatar, " +
            "a.evenement.id) " +
            "FROM Avis a " +
            "LEFT JOIN a.client c " +
            "LEFT JOIN a.visiteur v " +
            "WHERE a.evenement.id = :eventId")
    Page<AvisResponseDto> findAvisByEvenementId(@Param("eventId") Long eventId, Pageable pageable);
    // Vérifie si le client a déjà laissé un avis pour cet événement
    boolean existsByClientIdAndEvenementId(Long clientId, Long evenementId);
}
