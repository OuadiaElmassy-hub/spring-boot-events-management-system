package com.ouadia.rovista1.entities;

import com.ouadia.rovista1.entities.enums.StatutCompte;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@Data // pour les methode getter, setter, toString() , hachcode() ,equals()
@NoArgsConstructor @AllArgsConstructor
@Builder
public class Admin extends Utilisateur{
@NotEmpty
private String nom;
@NotEmpty
private String prenom;
@NotEmpty
private LocalDate dateNaissance;

    public Admin(Long id, String username, String email, String motDePasse, StatutCompte statutCompte, String phone,
                    String adresse, List<Notification> notifications, List<Role> roles, String nom, String prenom,
                    LocalDate dateNaissance) {
        super(id, username, email, motDePasse, statutCompte, phone, adresse, notifications, roles);
        this.nom = nom;
        this.prenom = prenom;
        this.dateNaissance = dateNaissance;
    }


    }



