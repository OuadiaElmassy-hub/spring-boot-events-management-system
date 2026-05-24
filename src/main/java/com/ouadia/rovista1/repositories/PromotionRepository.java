package com.ouadia.rovista1.repositories;

import com.ouadia.rovista1.entities.Admin;
import com.ouadia.rovista1.entities.Promotion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PromotionRepository extends JpaRepository<Promotion,Long> {
}
