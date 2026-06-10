package com.ouadia.rovista1.repositories;

import com.ouadia.rovista1.entities.Client;
import com.ouadia.rovista1.entities.Favorie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface FavorieRepository extends JpaRepository<Favorie,Long> {
    List<Favorie> findByClientId(Long clientId);
    Favorie findByClient(Client client);

    Page<Favorie> findByClientIdOrderByDateCreationDesc(Long ClientId, Pageable pageable);

    boolean existsByClientIdAndEvenementId(Long clientId, Long evenementId);

    Optional<Favorie> findByClientIdAndEvenementId(Long clientId, Long evenementId);

    long countByClientId(Long clientId);

    // Pour savoir si un event est favori dans une liste d'ids
    List<Favorie> findByClientIdAndEvenementIdIn(Long clientId, List<Long> evenementIds);
}
