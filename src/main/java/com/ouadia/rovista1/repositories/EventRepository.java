package com.ouadia.rovista1.repositories;
import com.ouadia.rovista1.dtos.evenement.EvenementResponseDto;
import com.ouadia.rovista1.entities.Evenement;
import com.ouadia.rovista1.entities.enums.StatutEvenement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Evenement,Long>,
        JpaSpecificationExecutor<Evenement> {

    // version 2

    Page<EvenementResponseDto> findByStatutEvenement(StatutEvenement statutEvenement, Pageable pageable);
    List<Evenement> findByStatutEvenement(StatutEvenement statut);

    List<Evenement> findByOrganisateurId(Long organisateurId);
    Page<Evenement> findByVilleIgnoreCase(String ville, Pageable pageable);
    List<Evenement> findByLieuSpecifique(String lieu);
    List<Evenement> findByPrix(double prix);
    List<Evenement> findByTitre(String titre);
    List<Evenement> findByDescription(String description);

    // ________________________________________

    @Query("SELECT DISTINCT e FROM Evenement e LEFT JOIN e.categorie c WHERE" +
            " LOWER(e.titre) LIKE LOWER(CONCAT('%', :keyword, '%')) OR" +
            " LOWER(e.description) LIKE LOWER(CONCAT('%', :keyword, '%')) OR" +
            " LOWER(e.lieuSpecifique) LIKE LOWER(CONCAT('%', :keyword, '%')) OR" +
            " LOWER(c.nom) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Evenement> searchEvents(String keyword);

}
