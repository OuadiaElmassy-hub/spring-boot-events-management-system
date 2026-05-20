package com.ouadia.rovista1.repositories;

import com.ouadia.rovista1.entities.Categorie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CategorieRepository extends JpaRepository<Categorie,Long> {
    Categorie findByNom(String nom);
}
