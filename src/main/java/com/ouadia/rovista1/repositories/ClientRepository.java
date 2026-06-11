package com.ouadia.rovista1.repositories;

import com.ouadia.rovista1.entities.Admin;
import com.ouadia.rovista1.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface ClientRepository extends JpaRepository<Client,Long> {


    boolean existsByEmail(String email);
    Optional<Client> findByEmail(String email);
   Optional<Client> findByUsername(String  username);
}
