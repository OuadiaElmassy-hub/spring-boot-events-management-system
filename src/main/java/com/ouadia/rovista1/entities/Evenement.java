package com.ouadia.rovista1.entities;

import com.ouadia.rovista1.entities.enums.StatutEvenement;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Evenement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String titre;
    @Column(columnDefinition = "TEXT")
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
    private String ville; //enum Ou class
    @Column(nullable = false)
    private int capacite;
    private double prix; // pour une place normale
    @CreationTimestamp
    private LocalDate dateCreation;
    private LocalDate dateValidation;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatutEvenement statutEvenement;

    @ManyToOne(fetch = FetchType.LAZY)
    private Organisateur organisateur;
    @OneToMany(mappedBy = "evenement") // par defaut : fetch = FetchType.LAZY
    private List<Reservation> reservations;
    @OneToMany(mappedBy = "evenement", fetch = FetchType.EAGER)
    private List<Avis> avis = new ArrayList<>();
    @ManyToOne(fetch = FetchType.LAZY)
    private Categorie categorie;
    @ManyToOne(fetch = FetchType.LAZY)
    private Promotion promotion;
    @ManyToMany(mappedBy = "evenements", fetch = FetchType.LAZY)
    private List<Favorie> favories;
    @OneToMany(mappedBy = "evenement", fetch = FetchType.EAGER)
    private List<Image> images = new ArrayList<>();
}