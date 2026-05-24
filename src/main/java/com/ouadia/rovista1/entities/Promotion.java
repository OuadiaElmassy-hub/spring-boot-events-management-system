package com.ouadia.rovista1.entities;

import com.ouadia.rovista1.entities.enums.TypePromotion;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
@Entity
@Data // pour les methode getter, setter, toString() , hachcode() ,equals()
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Promotion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String titre;
    @Column(nullable = false)
    private LocalDateTime dateDebut;
    @Column(nullable = false)
    private LocalDateTime dateFin;
    @Column(nullable = false)
    private TypePromotion type;
    @Column(nullable = false)
    private boolean estApprove;

    @ManyToOne
    private Organisateur organisateur;
    @ManyToMany
    private List<Client> clients;
    @OneToMany(mappedBy = "promotion")
    private List<Evenement> evenements;


}
