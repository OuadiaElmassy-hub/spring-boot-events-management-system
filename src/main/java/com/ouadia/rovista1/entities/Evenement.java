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
    @Column(nullable = false)
    private String titre;
    @Column(nullable = false,columnDefinition = "text")
    private String description;
    @Column(nullable = false)
    private LocalDate dateDebut;
    @Column(nullable = false)
    private LocalDate dateFin;
    @Column(nullable = false)
    private LocalTime heureDebut;
    @Column(nullable = false)
    private String lieuSpecifique;
    @Column(nullable = false)
    private String ville; //enum
    @Column(nullable = false)
    private int capacite;
    @Column(nullable = false)
    private double prix;
    @Column(nullable = false)
    // pour une place normale
    @Enumerated(EnumType.STRING)
    private StatutEvenement statutEvenement;
    @Column(nullable = false)
    private String fichierUri;
    @Column(nullable = false)
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
