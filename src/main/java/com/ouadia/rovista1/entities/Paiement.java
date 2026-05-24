package com.ouadia.rovista1.entities;

import com.ouadia.rovista1.entities.enums.MethodePaiement;
import com.ouadia.rovista1.entities.enums.StatutPaiement;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
@Entity
@Data // pour les methode getter, setter, toString() , hachcode() ,equals()
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Paiement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(nullable = false)
    private BigDecimal montant;
    @Column(nullable = false)
    private LocalDateTime datePaiement;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private StatutPaiement statut;
    @Enumerated(EnumType.STRING)
    private MethodePaiement methodePaiement;

    @OneToOne
    private Reservation reservation;


}
