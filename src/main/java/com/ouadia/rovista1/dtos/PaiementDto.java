package com.ouadia.rovista1.dtos;

import com.ouadia.rovista1.entities.enums.MethodePaiement;
import com.ouadia.rovista1.entities.enums.StatutPaiement;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class PaiementDto {

    private int id;
    private BigDecimal montant;
    private LocalDateTime datePaiement;
    private StatutPaiement statut;
    private MethodePaiement methodePaiement;

    public PaiementDto() {
    }

    public PaiementDto(int id, BigDecimal montant, LocalDateTime datePaiement,
                    StatutPaiement statut, MethodePaiement methode) {
        this.id = id;
        this.montant = montant;
        this.datePaiement = datePaiement;
        this.statut = statut;
        this.methodePaiement = methode;
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
}
