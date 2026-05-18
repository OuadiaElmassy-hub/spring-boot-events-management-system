package com.ouadia.rovista1.entities;

import com.ouadia.rovista1.entities.enums.StatutCompte;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

@Entity
public class Client extends Utilisateur{

    private String nom;
    private String prenom;
    private LocalDate dateNaissance;

    @ManyToMany(mappedBy = "clients")
    private List<Promotion> promotions;
    @OneToMany(mappedBy = "client")
    private List<Reservation> reservations;
    @OneToMany(mappedBy = "client")
    private List<Avis> avis;
    @OneToMany(mappedBy = "client")
    private List<Favorie> favories;

    public Client() {}

    public Client(String nom, String prenom, LocalDate dateNaissance, List<Promotion> promotions, List<Reservation> reservations, List<Avis> avis, List<Favorie> favories) {
        this.nom = nom;
        this.prenom = prenom;
        this.dateNaissance = dateNaissance;
        this.promotions = promotions;
        this.reservations = reservations;
        this.avis = avis;
        this.favories = favories;
    }

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

    public String getPrenom() {
        return prenom;
    }
    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public LocalDate getDateNaissance() {
        return dateNaissance;
    }

    public void setDateNaissance(LocalDate dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    public List<Promotion> getPromotions() {
        return promotions;
    }

    public void setPromotions(List<Promotion> promotions) {
        this.promotions = promotions;
    }

    public List<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(List<Reservation> reservations) {
        this.reservations = reservations;
    }

    public List<Avis> getAvis() {
        return avis;
    }

    public void setAvis(List<Avis> avis) {
        this.avis = avis;
    }

    public List<Favorie> getFavories() {
        return favories;
    }

    public void setFavories(List<Favorie> favories) {
        this.favories = favories;
    }
}
