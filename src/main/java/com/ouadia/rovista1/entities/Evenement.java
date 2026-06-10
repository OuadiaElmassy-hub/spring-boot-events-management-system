package com.ouadia.rovista1.entities;

import com.ouadia.rovista1.entities.enums.StatutEvenement;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
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
    @Column(nullable = false,columnDefinition = "text")
    private String description;
    @Column(nullable = false)
    private LocalDateTime dateDebut;
    @Column(nullable = false)
    private LocalDateTime dateFin;
    @Column(nullable = false)
    private String lieuSpecifique;
    @Column(nullable = false)
    private String ville; //enum Ou class
    @Column(nullable = false)
    private int capacite;

    private int placesRestants;
    @Column(nullable = false)
    private double prix; // pour une place normale
    @CreationTimestamp
    private LocalDateTime dateCreation;
    private LocalDateTime dateValidation;
    private LocalDateTime dateModification;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatutEvenement statutEvenement;
    private String motif;

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
    @ManyToMany(mappedBy = "evenement", fetch = FetchType.LAZY)
    private List<Favorie> favories;
    @OneToMany(mappedBy = "evenement", fetch = FetchType.EAGER)
    private List<Image> images = new ArrayList<>();

    @PrePersist void onCreate() { this.dateCreation = LocalDateTime.now(); }
}