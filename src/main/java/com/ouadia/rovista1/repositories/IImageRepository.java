package com.ouadia.rovista1.repositories;

import com.ouadia.rovista1.entities.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IImageRepository extends JpaRepository<Image, Long> {
}
