package com.ouadia.rovista1.services;

import com.ouadia.rovista1.entities.Evenement;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
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
}
