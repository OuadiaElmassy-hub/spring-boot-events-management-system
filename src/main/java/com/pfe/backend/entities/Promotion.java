package com.pfe.backend.entities;

import com.pfe.backend.entities.enums.StatutPromotion;
import com.pfe.backend.entities.enums.TypePromotion;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDate;
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
    private boolean estApprove;

    @ManyToOne
    private Organisateur organisateur;
    @ManyToMany
    private List<Client> clients;
    @OneToMany(mappedBy = "promotion", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Evenement> evenements;

    @Column(nullable = false, unique = true, length = 50)
    private String code;

    /** POURCENTAGE | MONTANT_FIXE */
    @Column(nullable = false, length = 20)
    @Builder.Default
    @Enumerated(value = EnumType.STRING)
    private TypePromotion type = TypePromotion.POURCENTAGE;

    @Column(nullable = false, length = 20)
    @Enumerated(value = EnumType.STRING)
    private StatutPromotion statutPromotion;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal valeur;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "evenement_id", nullable = false)
    private Evenement evenement;

    @Column(name = "date_debut")
    private LocalDate dateDebut;

    @Column(name = "date_fin")
    private LocalDate dateFin;

    @Column(name = "max_utilisations")
    private Integer maxUtilisations;

    @Column(name = "nb_utilisations", nullable = false)
    @Builder.Default
    private Integer nbUtilisations = 0;

    @Column(name = "montant_minimum", precision = 10, scale = 2)
    private BigDecimal montantMinimum;

    @Column(nullable = false)
    @Builder.Default
    private Boolean active = true;

    @Column(nullable = false)
    @Builder.Default
    private Boolean valid = true;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    // ── Helper : la promo est-elle encore valide ? ────────────────
    public boolean isValide() {
        LocalDate today = LocalDate.now();
        if (dateDebut != null && today.isBefore(dateDebut)) return false;
        if (dateFin   != null && today.isAfter(dateFin))    return false;
        if (maxUtilisations != null && nbUtilisations >= maxUtilisations) return false;
        return true;
    }

}
