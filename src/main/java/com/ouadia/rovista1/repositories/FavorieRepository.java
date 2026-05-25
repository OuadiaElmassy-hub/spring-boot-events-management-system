package com.ouadia.rovista1.repositories;

import com.ouadia.rovista1.dtos.FavorieDto;
import com.ouadia.rovista1.entities.Admin;
import com.ouadia.rovista1.entities.Client;
import com.ouadia.rovista1.entities.Favorie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface FavorieRepository extends JpaRepository<Favorie,Long> {
    List<Favorie> findByClientId(Long clientId);
    boolean existsByClientIdAndEvenementId(Long clientId, Long evenementId);
    void deleteByClientIdAndEvenementId(Long clientId, Long evenementId);
    Favorie findByClient(Client client);
}
