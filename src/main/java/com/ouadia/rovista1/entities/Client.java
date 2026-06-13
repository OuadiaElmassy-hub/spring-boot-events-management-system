package com.ouadia.rovista1.entities;

import com.ouadia.rovista1.entities.enums.StatutCompte;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.List;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@DiscriminatorValue("CLIENT")
@PrimaryKeyJoinColumn(name = "utilisateur_id")
public class Client extends Utilisateur{

    private LocalDate dateNaissance;

    @ManyToMany(mappedBy = "clients" )
    private List<Promotion> promotions;
    @OneToMany(mappedBy = "client")
    private List<Reservation> reservations;
    @OneToMany(mappedBy = "client")
    private List<Avis> avis;
    @OneToMany(mappedBy = "client")
    private List<Favorie> favories;



    public Client(Long id, String username, String nom, String email, String motDePasse, StatutCompte statutCompte, String phone, String adresse, List<Notification> notifications, List<Role> roles, String prenom, LocalDate dateNaissance, List<Promotion> promotions, List<Reservation> reservations, List<Avis> avis, List<Favorie> favories) {
        super(id, username, nom, prenom, email, motDePasse, statutCompte, phone, adresse);
        this.dateNaissance = dateNaissance;
        this.promotions = promotions;
        this.reservations = reservations;
        this.avis = avis;
        this.favories = favories;
    }

    public Client(Long id, String username, String email, String motDePasse, StatutCompte statutCompte, String phone,
                  String adresse, String nom, String prenom, LocalDate dateNaissance, List<Promotion> promotions,
                  List<Reservation> reservations, List<Avis> avis, List<Favorie> favories) {
        super(id, username, nom, prenom, email, motDePasse, statutCompte, phone, adresse);
        this.dateNaissance = dateNaissance;
        this.promotions = promotions;
        this.reservations = reservations;
        this.avis = avis;
        this.favories = favories;
    }


    public Client(Long id, String username, String email, String motDePasse, StatutCompte statutCompte, String phone, String adresse, String nom, String prenom) {
        super(id, username, nom, prenom, email, motDePasse, statutCompte, phone, adresse);
    }
}
