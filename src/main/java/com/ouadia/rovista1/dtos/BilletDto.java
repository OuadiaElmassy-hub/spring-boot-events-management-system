package com.ouadia.rovista1.dtos;

import com.ouadia.rovista1.entities.enums.TypeBillet;

import java.time.LocalDateTime;

public class BilletDto {

    private Long id;
    private String code;
    private String qrCode;
    private LocalDateTime dateBillet;
    private TypeBillet type;

    public BilletDto() {}

    public BilletDto(Long id, String code, String qrCode, LocalDateTime dateBillet, TypeBillet type) {
        this.id = id;
        this.code = code;
        this.qrCode = qrCode;
        this.dateBillet = dateBillet;
        this.type = type;
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
}