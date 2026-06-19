package com.pfe.backend.entities;

import com.pfe.backend.entities.enums.StatutReservation;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
@Entity
@Getter
@Setter
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

    @OneToMany(mappedBy = "reservation", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Billet> billets;
    @OneToOne(mappedBy = "reservation", cascade = CascadeType.ALL, orphanRemoval = true)
    private Paiement paiement;
    @ManyToOne
    private Evenement evenement;
    @ManyToOne
    private VisiteurInvite visiteurInvite;
    @ManyToOne
    private Client client;

    @PrePersist
    void onCreate() { this.dateReservation = LocalDateTime.now(); }

}