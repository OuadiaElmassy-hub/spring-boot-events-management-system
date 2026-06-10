package com.ouadia.rovista1.repositories;

import com.ouadia.rovista1.entities.Categorie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface CategorieRepository extends JpaRepository<Categorie,Long> {

    Optional<Categorie> findByNom(String nom);

}
