package com.ouadia.rovista1.services;

import com.ouadia.rovista1.entities.Evenement;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class EvenementSpecification {
    public static Specification<Evenement> serch(String ville, Long categorieId, LocalDate date){
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (ville != null){
                predicates.add(criteriaBuilder.equal(criteriaBuilder.lower(root.get("ville")), ville.toLowerCase()));
            }
            if (categorieId != null){
                predicates.add(criteriaBuilder.equal(root.get("categorie").get("id"), categorieId));
            }
            if (date != null){
                predicates.add(criteriaBuilder.equal(root.get("dateDebut"), date));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

    public static Specification<Evenement> filter(Long categorieId, String keyword, String ville, LocalDate date, Double prixMax) {

        return (root, query, cb) -> {

            List<Predicate> predicates = new ArrayList<>();

            // =====================
            // CATEGORY FILTER
            // =====================
            if (categorieId != null && categorieId != 0) {
                predicates.add(
                        cb.equal(root.get("categorie").get("id"), categorieId)
                );
            }
            // sinon => pas de filtre => toutes catégories

            // =====================
            // KEYWORD SEARCH (GLOBAL)
            // =====================
            if (keyword != null && !keyword.isEmpty()) {

                String k = "%" + keyword.toLowerCase() + "%";

                Predicate title = cb.like(cb.lower(root.get("titre")), k);
                Predicate desc = cb.like(cb.lower(root.get("description")), k);
                Predicate villek = cb.like(cb.lower(root.get("ville")), k);
                Predicate lieu = cb.like(cb.lower(root.get("lieuSpecifique")), k);

                predicates.add(cb.or(title, desc, villek, lieu));
            }

            // =====================
            // LIEU
            // =====================
            if (ville != null && !ville.isEmpty()) {
                predicates.add(
                        cb.like(cb.lower(root.get("ville")), "%" + ville.toLowerCase() + "%")
                );
            }

            // =====================
            // DATE
            // =====================
            if (date != null) {
                LocalDateTime startOfDay = date.atStartOfDay();
                LocalDateTime endOfDay = date.atTime(23, 59, 59);
                predicates.add(cb.between(root.get("dateDebut"), startOfDay, endOfDay));
            }

            // =====================
            // PRIX MAX
            // =====================
            if (prixMax != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("prix"), prixMax));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
