package com.ouadia.rovista1.entities;

import com.ouadia.rovista1.entities.enums.StatutCompte;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

import jakarta.persistence.PrimaryKeyJoinColumn;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor @AllArgsConstructor
@SuperBuilder
@DiscriminatorValue("ADMIN")
@PrimaryKeyJoinColumn(name = "utilisateur_id")
public class Admin extends Utilisateur{

    @Column(nullable = false)
    private String prenom;
    private LocalDate dateNaissance;

    public Admin(Long id, String username, String email, String motDePasse, StatutCompte statutCompte, String phone,
                    String adresse, List<Notification> notifications, List<Role> roles, String nom, String prenom,
                    LocalDate dateNaissance) {
        super(id, username, nom, email, motDePasse, statutCompte, phone, adresse, notifications, roles);
        this.prenom = prenom;
        this.dateNaissance = dateNaissance;
    }
}



