package com.ouadia.rovista1.entities;

import com.ouadia.rovista1.entities.enums.StatutEvenement;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data // pour les methode getter, setter, toString() , hachcode() ,equals()
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Evenement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotEmpty
    private String titre;
    @NotEmpty
    private String description;
    @NotEmpty
    private LocalDate dateDebut;
    @NotEmpty
    private LocalDate dateFin;
    @NotEmpty
    private LocalTime heureDebut;
    @NotEmpty
    private String lieuSpecifique;
    @NotEmpty
    private String ville; //enum
    @NotEmpty
    private int capacite;
    @NotEmpty
    private double prix;
    @NotEmpty
    // pour une place normale
    @Enumerated(EnumType.STRING)
    private StatutEvenement statutEvenement;
    @NotEmpty
    private String fichierUri;
    @NotEmpty
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
    @ManyToMany(mappedBy = "evenements" )
    private List<Favorie> favories;
    @OneToMany(mappedBy = "evenement")
    private List<Image> images;




}
