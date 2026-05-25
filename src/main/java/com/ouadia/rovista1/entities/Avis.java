package com.ouadia.rovista1.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data // pour les methode getter, setter, toString() , hachcode() ,equals()
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Avis {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false,columnDefinition = "text")
    private String comment;
    @Column(nullable = false)
    private double note;
    @Column(nullable = false)
    private LocalDate dateAvis;

    @ManyToOne
    private Evenement evenement;
    @ManyToOne
    private Client client;
    @ManyToOne
    private VisiteurInvite visiteur;


}
