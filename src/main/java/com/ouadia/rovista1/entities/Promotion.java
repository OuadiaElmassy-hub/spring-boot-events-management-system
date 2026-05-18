package com.ouadia.rovista1.entities;

import com.ouadia.rovista1.entities.enums.TypePromotion;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;
@Entity
public class Promotion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titre;
    private LocalDateTime dateDebut;
    private LocalDateTime dateFin;
    private TypePromotion type;
    private boolean estApprove;

    @ManyToOne
    private Organisateur organisateur;
    @ManyToMany
    private List<Client> clients;
    @OneToMany(mappedBy = "promotion")
    private List<Evenement> evenements;

    public Promotion(){
    }

    public Promotion(Long id, String titre, LocalDateTime dateDebut, LocalDateTime dateFin, TypePromotion type,
                     boolean estApprove, Organisateur organisateur, List<Client> clients, List<Evenement> evenements) {
        this.id = id;
        this.titre = titre;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.type = type;
        this.estApprove = estApprove;
        this.organisateur = organisateur;
        this.clients = clients;
        this.evenements = evenements;
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

    public Organisateur getOrganisateur() {
        return organisateur;
    }

    public void setOrganisateur(Organisateur organisateur) {
        this.organisateur = organisateur;
    }

    public List<Client> getClients() {
        return clients;
    }

    public void setClients(List<Client> clients) {
        this.clients = clients;
    }

    public List<Evenement> getEvenements() {
        return evenements;
    }

    public void setEvenements(List<Evenement> evenements) {
        this.evenements = evenements;
    }
}
