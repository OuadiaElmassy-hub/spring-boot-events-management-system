package com.ouadia.rovista1.entities;

import com.ouadia.rovista1.entities.enums.TypeBillet;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Billet {

    // les attributs necessaires de l'entité :
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String code;
    private String qrCode;
    private LocalDateTime dateBillet;
    @Enumerated(EnumType.STRING)
    private TypeBillet type;

    //les attributes des relations :
    @ManyToOne
    private Reservation reservation;

    public Billet() {}

    public Billet(Long id, String code, String qrCode, LocalDateTime dateBillet,
                  TypeBillet type, Reservation reservation) {
        this.id = id;
        this.code = code;
        this.qrCode = qrCode;
        this.dateBillet = dateBillet;
        this.type = type;
        this.reservation = reservation;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getQrCode() {
        return qrCode;
    }

    public void setQrCode(String qrCode) {
        this.qrCode = qrCode;
    }

    public LocalDateTime getDateBillet() {
        return dateBillet;
    }

    public void setDateBillet(LocalDateTime dateBillet) {
        this.dateBillet = dateBillet;
    }

    public TypeBillet getType() {
        return type;
    }

    public void setType(TypeBillet type) {
        this.type = type;
    }

    public Reservation getReservation() {
        return reservation;
    }

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }
}