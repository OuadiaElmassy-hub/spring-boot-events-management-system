package com.ouadia.rovista1.dtos;

import com.ouadia.rovista1.entities.enums.Categorie;
import com.ouadia.rovista1.entities.enums.StatutEvenement;

import java.time.LocalDate;
import java.time.LocalTime;

public class EvenementDto {

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
    private StatutEvenement statutEvenement;
    private Categorie categorie;
    private String fichierUri;
    private String imageUri;

    public EvenementDto() {}

    public EvenementDto(Long id, String titre, String description, LocalDate dateDebut, LocalDate dateFin,
                     LocalTime heureDebut, String lieuSpecifique, String ville, int capacite, double prix,
                     StatutEvenement statutEvenement, String fichierUri, String imageUri) {
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
}
