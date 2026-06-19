package com.pfe.backend.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data // pour les methode getter, setter, toString() , hachcode() ,equals()
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VisiteurInvite {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;
    @Column(nullable = false)
    private String nom;
    @Column(nullable = false)
    private  String prenom;
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private String phone;
    @Column(nullable = false)
    private String adresse;

    @OneToMany(mappedBy = "visiteurInvite")
    private List<Reservation> reservations;
    @OneToMany(mappedBy = "visiteur")
    private List<Avis> avis;


}
