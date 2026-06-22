package com.pfe.backend.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
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
    private LocalDateTime dateAvis;

    @ManyToOne
    private Evenement evenement;
    @ManyToOne
    private Client client;
    @ManyToOne
    private VisiteurInvite visiteur;


}
