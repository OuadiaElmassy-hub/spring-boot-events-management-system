package com.ouadia.rovista1.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
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
    @NotEmpty
    private String nom;
    @NotEmpty
    private  String prenom;
    @NotEmpty
    private String email;
    @NotEmpty
    private String phone;
    @NotEmpty
    private String adresse;

    @OneToMany(mappedBy = "visiteurInvite")
    private List<Reservation> reservations;
    @OneToMany(mappedBy = "visiteur")
    private List<Avis> avis;


}
