package com.ouadia.rovista1.repositories;

import com.ouadia.rovista1.entities.Favorie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface FavorieRepository extends JpaRepository<Favorie,Long> {
}
