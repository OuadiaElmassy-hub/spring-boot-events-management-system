package com.pfe.backend.dtos;

import com.pfe.backend.entities.Notification;
import com.pfe.backend.entities.Role;
import com.pfe.backend.entities.Utilisateur;
import com.pfe.backend.entities.enums.StatutCompte;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Setter
@Getter
public class AdminDto extends Utilisateur {
    @NotNull
    private String nom;
    @NotNull
    private String prenom;
    @NotNull
    private LocalDate dateNaissance;

    public AdminDto(String nom, String prenom, LocalDate dateNaissance) {
        this.nom = nom;
        this.prenom = prenom;
        this.dateNaissance = dateNaissance;
    }

    public AdminDto() {
    }

    public AdminDto(Long id, String username, String email, String motDePasse, StatutCompte statutCompte, String phone,
                    String adresse, List<Notification> notifications, List<Role> roles, String nom, String prenom,
                    LocalDate dateNaissance) {
        super(id, username, nom, prenom, email, motDePasse, statutCompte, phone, adresse, notifications, roles);
        this.dateNaissance = dateNaissance;
    }
}
