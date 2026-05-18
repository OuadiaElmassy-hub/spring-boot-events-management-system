package com.ouadia.rovista1.repositories;

import com.ouadia.rovista1.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client,Long> {
}
