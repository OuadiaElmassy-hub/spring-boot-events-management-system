package com.ouadia.rovista1.entities;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class Avis {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String comment;
    private double note;
    private LocalDate dateAvis;

    @ManyToOne
    private Evenement evenement;
    @ManyToOne
    private Client client;
    @ManyToOne
    private VisiteurInvite visiteur;

    public Avis(){
    }

    public Avis(Long id, String comment, double note, LocalDate dateAvis,
                Evenement evenement, Client client, VisiteurInvite visiteur) {
        this.id = id;
        this.comment = comment;
        this.note = note;
        this.dateAvis = dateAvis;
        this.evenement = evenement;
        this.client = client;
        this.visiteur = visiteur;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public double getNote() {
        return note;
    }

    public void setNote(double note) {
        this.note = note;
    }

    public LocalDate getDateAvis() {
        return dateAvis;
    }

    public void setDateAvis(LocalDate dateAvis) {
        this.dateAvis = dateAvis;
    }

    public Evenement getEvenement() {
        return evenement;
    }

    public void setEvenement(Evenement evenement) {
        this.evenement = evenement;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public VisiteurInvite getVisiteur() {
        return visiteur;
    }

    public void setVisiteur(VisiteurInvite visiteur) {
        this.visiteur = visiteur;
    }
}
