package com.ouadia.rovista1.entities;

import com.ouadia.rovista1.entities.enums.StatutReservation;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
@Entity
@Data // pour les methode getter, setter, toString() , hachcode() ,equals()
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private LocalDateTime dateReservation;
    @Column(nullable = false)
    private int nombrePlaces;
    @Column(nullable = false)
    private StatutReservation statut;
    @Column(nullable = false)
    private BigDecimal montant;

    @OneToMany(mappedBy = "reservation")
    private List<Billet> billets;
    @OneToOne(mappedBy = "reservation")
    private Paiement paiement;
    @ManyToOne
    private Evenement evenement;
    @ManyToOne
    private VisiteurInvite visiteurInvite;
    @ManyToOne
    private Client client;


}
