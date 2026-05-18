package com.ouadia.rovista1.entities;

import com.ouadia.rovista1.entities.enums.StatutReservation;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
@Entity
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime dateReservation;
    private int nombrePlaces;
    private StatutReservation statut;
    private BigDecimal montant;

    @OneToMany(mappedBy = "reservation")
    private List<Billet> billets;
    @OneToOne(mappedBy = "reservation")
    private Paiement paiement;
    @ManyToOne
    private Evenement evenement;
    @ManyToOne
    private VisiteurInvite visiteurInvite;
    @ManyToOne
    private Client client;

    public Reservation() {}

    public Reservation(Long id, LocalDateTime dateReservation, int nombrePlaces, StatutReservation statut,
                       BigDecimal montant, List<Billet> billets, Paiement paiement, Evenement evenement,
                       VisiteurInvite visiteurInvite, Client client) {
        this.id = id;
        this.dateReservation = dateReservation;
        this.nombrePlaces = nombrePlaces;
        this.statut = statut;
        this.montant = montant;
        this.billets = billets;
        this.paiement = paiement;
        this.evenement = evenement;
        this.visiteurInvite = visiteurInvite;
        this.client = client;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getDateReservation() {
        return dateReservation;
    }

    public void setDateReservation(LocalDateTime dateReservation) {
        this.dateReservation = dateReservation;
    }

    public int getNombrePlaces() {
        return nombrePlaces;
    }

    public void setNombrePlaces(int nombrePlaces) {
        this.nombrePlaces = nombrePlaces;
    }

    public StatutReservation getStatut() {
        return statut;
    }

    public void setStatut(StatutReservation statut) {
        this.statut = statut;
    }

    public BigDecimal getMontant() {
        return montant;
    }

    public void setMontant(BigDecimal montant) {
        this.montant = montant;
    }

    public List<Billet> getBillets() {
        return billets;
    }

    public void setBillets(List<Billet> billets) {
        this.billets = billets;
    }

    public Paiement getPaiement() {
        return paiement;
    }

    public void setPaiement(Paiement paiement) {
        this.paiement = paiement;
    }

    public Evenement getEvenement() {
        return evenement;
    }

    public void setEvenement(Evenement evenement) {
        this.evenement = evenement;
    }

    public VisiteurInvite getVisiteurInvite() {
        return visiteurInvite;
    }

    public void setVisiteurInvite(VisiteurInvite visiteurInvite) {
        this.visiteurInvite = visiteurInvite;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }
}
