package com.ouadia.rovista1.dtos;

import com.ouadia.rovista1.entities.enums.TypePromotion;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;
public class PromotionDto {

    private Long id;
    private String titre;
    private LocalDateTime dateDebut;
    private LocalDateTime dateFin;
    private TypePromotion type;
    private boolean estApprove;

    public PromotionDto(){
    }

    public PromotionDto(Long id, String titre, LocalDateTime dateDebut,
                     LocalDateTime dateFin, TypePromotion type, boolean estApprove) {
        this.id = id;
        this.titre = titre;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.type = type;
        this.estApprove = estApprove;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public LocalDateTime getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(LocalDateTime dateDebut) {
        this.dateDebut = dateDebut;
    }

    public LocalDateTime getDateFin() {
        return dateFin;
    }

    public void setDateFin(LocalDateTime dateFin) {
        this.dateFin = dateFin;
    }

    public TypePromotion getType() {
        return type;
    }

    public void setType(TypePromotion type) {
        this.type = type;
    }

    public boolean isEstApprove() {
        return estApprove;
    }

    public void setEstApprove(boolean estApprove) {
        this.estApprove = estApprove;
    }
}
