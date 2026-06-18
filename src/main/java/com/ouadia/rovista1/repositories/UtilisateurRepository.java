package com.ouadia.rovista1.repositories;

import com.ouadia.rovista1.entities.Admin;
import com.ouadia.rovista1.entities.Utilisateur;
import org.springframework.context.support.BeanDefinitionDsl.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;


@Repository
public interface UtilisateurRepository extends JpaRepository<Utilisateur,Long> {

    Optional<Utilisateur> findByEmail(String email);
    boolean existsByEmail(String email);
    boolean existsByUsername(String username);

    //LOWER(u.nom) LIKE LOWER(CONCAT('%', :search, '%')) OR
    @Query("""
        SELECT DISTINCT u FROM Utilisateur u
        JOIN u.roles r
        WHERE (:search IS NULL OR LOWER(u.email) LIKE LOWER(CONCAT('%', :search, '%')))
        OR (:search IS NULL OR LOWER(u.nom) LIKE LOWER(CONCAT('%', :search, '%')))
        AND   (:role IS NULL OR r.roleName LIKE LOWER(CONCAT('%', :role, '%'))))
    """)
    Page<Utilisateur> search(
            @Param("search") String search,
            @Param("role") Role role,
            Pageable pageable
    );

    long countByCreatedAtAfter(LocalDateTime date);

    @Query("""
        SELECT count(u) FROM Utilisateur u
        JOIN u.roles r
        WHERE  r.roleName = :role
    """)
    long countByRole(
            @Param("role")   String role
    );

    @Query("SELECT DISTINCT u FROM Utilisateur u LEFT JOIN FETCH u.roles WHERE u.username = :username")
    Optional<Utilisateur> findByUsernameWithRoles(@Param("username") String username);

    Optional<Utilisateur> findByUsername(String username);
//    Utilisateur findByUsername(String username);
}
