package com.ouadia.rovista1.repositories;

import com.ouadia.rovista1.entities.Categorie;
import com.ouadia.rovista1.entities.Evenement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface CategorieRepository extends JpaRepository<Categorie,Long> {

    Optional<Categorie> findByNom(String nom);

    @Query("SELECT DISTINCT c FROM Categorie c " +
            "WHERE (:keyword IS NULL OR :keyword = '' " +
            "   OR LOWER(c.nom) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "   OR LOWER(c.description) LIKE LOWER(CONCAT('%', :keyword, '%')))")
    Page<Categorie> searchCategories(@Param("keyword") String keyword, Pageable pageable);

    Page<Categorie> findByNomContainingIgnoreCase(String nom, Pageable pageable);

    @Query("SELECT COUNT(e) FROM Evenement e WHERE e.categorie.id = :id")
    Long countEvenements(@Param("id") Long id);

    @Query("SELECT c.id, COUNT(e) FROM Evenement e JOIN e.categorie c WHERE c.id IN :ids GROUP BY c.id")
    List<Object[]> countEvenementsByCategorieIds(@Param("ids") List<Long> ids);


    boolean existsByNomIgnoreCase(String nom);
    Optional<Categorie> findByNomIgnoreCase(String nom);

}
