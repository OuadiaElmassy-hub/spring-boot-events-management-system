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
    @NotEmpty
    private String titre;
    @NotEmpty
    private LocalDateTime dateDebut;
    @NotEmpty
    private LocalDateTime dateFin;
    @NotEmpty
    private TypePromotion type;
    @NotEmpty
    private boolean estApprove;

    @ManyToOne
    private Organisateur organisateur;
    @ManyToMany
    private List<Client> clients;
    @OneToMany(mappedBy = "promotion")
    private List<Evenement> evenements;


}
