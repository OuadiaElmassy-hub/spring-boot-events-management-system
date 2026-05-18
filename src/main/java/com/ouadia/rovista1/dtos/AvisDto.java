package com.ouadia.rovista1.dtos;

import java.time.LocalDate;

public class AvisDto {

    private Long id;
    private String comment;
    private double note;
    private LocalDate dateAvis;

    public AvisDto(){
    }

    public AvisDto(Long id, String comment, double note, LocalDate dateAvis) {
        this.id = id;
        this.comment = comment;
        this.note = note;
        this.dateAvis = dateAvis;
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
}
