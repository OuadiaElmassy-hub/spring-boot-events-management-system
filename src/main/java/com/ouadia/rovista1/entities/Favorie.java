package com.ouadia.rovista1.entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
public class Favorie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;
    private String description;
    private LocalDateTime dateCreation;

    @ManyToOne
    private Client client;
    @ManyToMany
    private List<Evenement> evenements;

    public Favorie() {
    }

    public Favorie(Long id, String description, LocalDateTime dateCreation, Client client, List<Evenement> evenements) {
        this.id = id;
        this.description = description;
        this.dateCreation = dateCreation;
        this.client = client;
        this.evenements = evenements;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(LocalDateTime dateCreation) {
        this.dateCreation = dateCreation;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public List<Evenement> getEvenements() {
        return evenements;
    }

    public void setEvenements(List<Evenement> evenements) {
        this.evenements = evenements;
    }
}
