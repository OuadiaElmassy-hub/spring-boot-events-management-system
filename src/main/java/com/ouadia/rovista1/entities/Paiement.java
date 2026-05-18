package com.ouadia.rovista1.entities;

import com.ouadia.rovista1.entities.enums.MethodePaiement;
import com.ouadia.rovista1.entities.enums.StatutPaiement;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
@Entity
public class Paiement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private BigDecimal montant;
    private LocalDateTime datePaiement;
    @Enumerated(EnumType.STRING)
    private StatutPaiement statut;
    @Enumerated(EnumType.STRING)
    private MethodePaiement methodePaiement;

    @OneToOne
    private Reservation reservation;

    public Paiement() {
    }

    public Paiement(int id, BigDecimal montant, LocalDateTime datePaiement,
                    StatutPaiement statut, MethodePaiement methode, Reservation reservation) {
        this.id = id;
        this.montant = montant;
        this.datePaiement = datePaiement;
        this.statut = statut;
        this.methodePaiement = methode;
        this.reservation = reservation;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public BigDecimal getMontant() {
        return montant;
    }
    public void setMontant(BigDecimal montant) {
        this.montant = montant;
    }

    public LocalDateTime getDatePaiement() {
        return datePaiement;
    }
    public void setDatePaiement(LocalDateTime datePaiement) {
        this.datePaiement = datePaiement;
    }

    public StatutPaiement getStatut() {
        return statut;
    }
    public void setStatut(StatutPaiement statut) {
        this.statut = statut;
    }

    public MethodePaiement getMethodePaiement() {
        return methodePaiement;
    }
    public void setMethodePaiement(MethodePaiement methode) {
        this.methodePaiement = methode;
    }

    public Reservation getReservation() {
        return reservation;
    }
    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }
}
