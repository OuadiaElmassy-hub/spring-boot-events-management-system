package com.ouadia.rovista1.entities;

import com.ouadia.rovista1.entities.enums.StatutCompte;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;

import lombok.*;
import lombok.experimental.SuperBuilder;


import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Inheritance(strategy = InheritanceType.JOINED)
public  class Utilisateur {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private String username;
    @Column(nullable = false)
    private String motDePasse;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private StatutCompte statutCompte;
    @Column(nullable = false)
    private String phone;
    @Column(nullable = false)
    private String adresse;

    @OneToMany(mappedBy = "destinataire")
    private List<Notification> notifications;
    @ManyToMany(fetch = FetchType.EAGER)
    private List<Role> roles = new ArrayList<>();




    public Utilisateur(Long id, String username, String email, String motDePasse,
                       StatutCompte statutCompte, String phone, String adresse) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.motDePasse = motDePasse;
        this.statutCompte = statutCompte;
        this.phone = phone;
        this.adresse = adresse;
    }

    public Utilisateur(String username, String email, String motDePasse,
                       StatutCompte statutCompte, String phone, String adresse) {
        this.username = username;
        this.email = email;
        this.motDePasse = motDePasse;
        this.statutCompte = statutCompte;
        this.phone = phone;
        this.adresse = adresse;
    }
    public Utilisateur (String username , String motDePasse){
        this.username=username;
        this.motDePasse=motDePasse;
    }

}
