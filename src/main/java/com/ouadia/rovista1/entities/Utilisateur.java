package com.ouadia.rovista1.entities;

import com.ouadia.rovista1.entities.enums.StatutCompte;
import jakarta.persistence.*;

import lombok.*;
import lombok.experimental.SuperBuilder;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "type", discriminatorType = DiscriminatorType.STRING)
public class Utilisateur {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private String nom;
    @Column(nullable = false)
    private String username;
    @Column(nullable = false)
    private String motDePasse;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private StatutCompte statutCompte;
    private boolean enabled;
    @Column(nullable = false)
    private String phone;
    @Column(nullable = false)
    private String adresse;

    private String avatar;
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "destinataire")
    private List<Notification> notifications;
    @ManyToMany(fetch = FetchType.LAZY)
    private List<Role> roles = new ArrayList<>();



    public Utilisateur(Long id, String username, String nom, String email, String motDePasse,
                       StatutCompte statutCompte, String phone, String adresse) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.nom = nom;
        this.motDePasse = motDePasse;
        this.statutCompte = statutCompte;
        this.phone = phone;
        this.adresse = adresse;
    }

    public Utilisateur(String username, String nom, String email, String motDePasse,
                       StatutCompte statutCompte, String phone, String adresse) {
        this.username = username;
        this.email = email;
        this.nom = nom;
        this.motDePasse = motDePasse;
        this.statutCompte = statutCompte;
        this.phone = phone;
        this.adresse = adresse;
    }
    public Utilisateur(Long id, String username, String nom, String email, String motDePasse,
                       StatutCompte statutCompte, String phone, String adresse, List<Notification> notifications, List<Role> roles) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.nom = nom;
        this.motDePasse = motDePasse;
        this.statutCompte = statutCompte;
        this.phone = phone;
        this.adresse = adresse;
        this.notifications = notifications;
        this.roles = roles;
    }}
