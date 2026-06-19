package com.pfe.backend.repositories;

import com.pfe.backend.entities.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IImageRepository extends JpaRepository<Image, Long> {
}
