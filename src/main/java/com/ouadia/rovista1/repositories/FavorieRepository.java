package com.ouadia.rovista1.repositories;

import com.ouadia.rovista1.entities.Client;
import com.ouadia.rovista1.entities.Favorie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface FavorieRepository extends JpaRepository<Favorie,Long> {
    List<Favorie> findByClientId(Long clientId);
    boolean existsByClientIdAndEvenementsId(Long clientId, Long evenementId);
    void deleteByClientIdAndEvenementsId(Long clientId, Long evenementId);
    Favorie findByClient(Client client);
}
