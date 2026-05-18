package com.ouadia.rovista1.entities;

import com.ouadia.rovista1.entities.enums.StatutEvenement;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Entity
public class Evenement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titre;
    private String description;
    private LocalDate dateDebut;
    private LocalDate dateFin;
    private LocalTime heureDebut;
    private String lieuSpecifique;
    private String ville; //enum
    private int capacite;
    private double prix; // pour une place normale
    @Enumerated(EnumType.STRING)
    private StatutEvenement statutEvenement;
    private String fichierUri;
    private String imageUri;

    @ManyToOne
    private Organisateur organisateur;
    @OneToMany(mappedBy = "evenement")
    private List<Reservation> reservations;
    @OneToMany(mappedBy = "evenement")
    private List<Avis> avis;
    @ManyToOne
    private Categorie categorie;
    @ManyToOne
    private Promotion promotion;
    @ManyToMany(mappedBy = "evenements")
    private List<Favorie> favories;

//    @OneToMany
//    private List<Image> images;

    public Evenement() {}

    public Evenement(Long id, String titre, String description, LocalDate dateDebut,
                     LocalDate dateFin, LocalTime heureDebut, String lieuSpecifique, String ville,
                     int capacite, double prix, StatutEvenement statutEvenement,
                     String fichierUri, String imageUri, Organisateur organisateur,
                     List<Reservation> reservations, List<Avis> avis, Categorie categorie,
                     Promotion promotion, List<Favorie> favories) {
        this.id = id;
        this.titre = titre;
        this.description = description;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.heureDebut = heureDebut;
        this.lieuSpecifique = lieuSpecifique;
        this.ville = ville;
        this.capacite = capacite;
        this.prix = prix;
        this.statutEvenement = statutEvenement;
        this.fichierUri = fichierUri;
        this.imageUri = imageUri;
        this.organisateur = organisateur;
        this.reservations = reservations;
        this.avis = avis;
        this.categorie = categorie;
        this.promotion = promotion;
        this.favories = favories;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitre() { return titre; }
    public void setTitre(String titre) { this.titre = titre; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public LocalDate getDateDebut() { return dateDebut; }
    public void setDateDebut(LocalDate dateDebut) { this.dateDebut = dateDebut; }

    public LocalDate getDateFin() { return dateFin; }
    public void setDateFin(LocalDate dateFin) { this.dateFin = dateFin; }

    public LocalTime getHeureDebut() {
        return heureDebut;
    }

    public void setHeureDebut(LocalTime heureDebut) {
        this.heureDebut = heureDebut;
    }

    public String getLieuSpecifique() { return lieuSpecifique; }
    public void setLieuSpecifique(String lieu) { this.lieuSpecifique = lieu; }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public int getCapacite() { return capacite; }
    public void setCapacite(int capacite) { this.capacite = capacite; }

    public double getPrix() { return prix; }
    public void setPrix(double prix) { this.prix = prix; }

    public StatutEvenement getStatutEvenement() { return statutEvenement; }
    public void setStatutEvenement(StatutEvenement statutEvenement) { this.statutEvenement = statutEvenement; }

    public String getImageUri() {
        return imageUri;
    }
    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }

    public String getFichierUri() {
        return fichierUri;
    }
    public void setFichierUri(String fichierUri) {
        this.fichierUri = fichierUri;
    }


    public Categorie getCategorie() {
        return categorie;
    }
    public void setCategorie(Categorie categorie) {
        this.categorie = categorie;
    }

    public Organisateur getOrganisateur() {
        return organisateur;
    }
    public void setOrganisateur(Organisateur organisateur) {
        this.organisateur = organisateur;
    }

    public List<Reservation> getReservations() {
        return reservations;
    }
    public void setReservations(List<Reservation> reservations) {
        this.reservations = reservations;
    }

    public List<Avis> getAvis() {
        return avis;
    }

    public void setAvis(List<Avis> avis) {
        this.avis = avis;
    }

    public Promotion getPromotion() {
        return promotion;
    }

    public void setPromotion(Promotion promotion) {
        this.promotion = promotion;
    }

    public List<Favorie> getFavories() {
        return favories;
    }

    public void setFavories(List<Favorie> favories) {
        this.favories = favories;
    }
}
