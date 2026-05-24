package com.ouadia.rovista1.entities;

import com.ouadia.rovista1.entities.enums.StatutCompte;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@Data // pour les methode getter, setter, toString() , hachcode() ,equals()
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Client extends Utilisateur{
    @Column(nullable = false)
    private String nom;
    @Column(nullable = false)
    private String prenom;
    @Column(nullable = false)
    private LocalDate dateNaissance;

    @ManyToMany(mappedBy = "clients" )
    private List<Promotion> promotions;
    @OneToMany(mappedBy = "client")
    private List<Reservation> reservations;
    @OneToMany(mappedBy = "client")
    private List<Avis> avis;
    @OneToMany(mappedBy = "client")
    private List<Favorie> favories;



    public Client(Long id, String username, String email, String motDePasse, StatutCompte statutCompte, String phone, String adresse, List<Notification> notifications, List<Role> roles, String nom, String prenom, LocalDate dateNaissance, List<Promotion> promotions, List<Reservation> reservations, List<Avis> avis, List<Favorie> favories) {
        super(id, username, email, motDePasse, statutCompte, phone, adresse, notifications, roles);
        this.nom = nom;
        this.prenom = prenom;
        this.dateNaissance = dateNaissance;
        this.promotions = promotions;
        this.reservations = reservations;
        this.avis = avis;
        this.favories = favories;
    }

    public Client(Long id, String username, String email, String motDePasse, StatutCompte statutCompte, String phone, String adresse, String nom, String prenom, LocalDate dateNaissance, List<Promotion> promotions, List<Reservation> reservations, List<Avis> avis, List<Favorie> favories) {
        super(id, username, email, motDePasse, statutCompte, phone, adresse);
        this.nom = nom;
        this.prenom = prenom;
        this.dateNaissance = dateNaissance;
        this.promotions = promotions;
        this.reservations = reservations;
        this.avis = avis;
        this.favories = favories;
    }


    public Client(Long id, String username, String email, String motDePasse, StatutCompte statutCompte, String phone, String adresse) {
        super(id, username, email, motDePasse, statutCompte, phone, adresse);
    }
}
