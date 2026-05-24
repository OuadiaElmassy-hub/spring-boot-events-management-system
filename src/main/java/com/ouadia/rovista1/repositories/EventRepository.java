package com.ouadia.rovista1.repositories;

import com.ouadia.rovista1.entities.Admin;
import com.ouadia.rovista1.entities.Evenement;
import com.ouadia.rovista1.entities.enums.StatutEvenement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Evenement,Long> {

    @Query("SELECT DISTINCT e FROM Evenement e LEFT JOIN e.categorie c WHERE" +
            " LOWER(e.titre) LIKE LOWER(CONCAT('%', :keyword, '%')) OR" +
            " LOWER(e.description) LIKE LOWER(CONCAT('%', :keyword, '%')) OR" +
            " LOWER(e.lieuSpecifique) LIKE LOWER(CONCAT('%', :keyword, '%')) OR" +
            " LOWER(c.nom) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Evenement> searchEvents(String keyword);

    //List<Evenement> findByCategorie(String categorie);
    List<Evenement> findByLieuSpecifique(String lieu);
    List<Evenement> findByPrix(double prix);
    List<Evenement> findByTitre(String titre);
    List<Evenement> findByDescription(String description);

    public List<Evenement> findByStatutEvenement(StatutEvenement statut);

//    @Query("")
//    List<Evenement> findByOrganisateur(Utilisateur organisateur);
}
