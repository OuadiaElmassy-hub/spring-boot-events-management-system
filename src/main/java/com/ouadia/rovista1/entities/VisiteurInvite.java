package com.ouadia.rovista1.entities;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class VisiteurInvite {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;
    private String nom;
    private  String prenom;
    private String email;
    private String phone;
    private String adresse;

    @OneToMany(mappedBy = "visiteurInvite")
    private List<Reservation> reservations;
    @OneToMany(mappedBy = "visiteur")
    private List<Avis> avis;

    public VisiteurInvite() {
    }

    public VisiteurInvite(Long id, String nom, String prenom, String email,
                          String phone, String adresse, List<Reservation> reservations, List<Avis> avis) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.phone = phone;
        this.adresse = adresse;
        this.reservations = reservations;
        this.avis = avis;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
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
}
