package com.ouadia.rovista1.dtos;

import com.ouadia.rovista1.entities.enums.StatutReservation;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class ReservationDto {

    private Long id;
    private LocalDateTime dateReservation;
    private int nombrePlaces;
    private StatutReservation statut;
    private BigDecimal montant;

    public ReservationDto() {}

    public ReservationDto(Long id, LocalDateTime dateReservation, int nombrePlaces,
                       StatutReservation statut, BigDecimal montant) {
        this.id = id;
        this.dateReservation = dateReservation;
        this.nombrePlaces = nombrePlaces;
        this.statut = statut;
        this.montant = montant;
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
}
