package com.pfe.backend.entities;

import com.pfe.backend.entities.enums.StatutCompte;
import com.pfe.backend.entities.enums.StatutOrganisateur;
import jakarta.persistence.*;

import lombok.*;
import lombok.experimental.SuperBuilder;


import java.time.LocalDateTime;
import java.util.List;
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@DiscriminatorValue("ORGANISATEUR")
@PrimaryKeyJoinColumn(name = "utilisateur_id")
public class Organisateur extends Utilisateur{

    //@Column(nullable = false)
    private Long numRegistre;

    private LocalDateTime dateValidation;
    @Column(nullable = false)
    private String  nomOrganisation;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private StatutOrganisateur statutOrganisateur;

    @Column(nullable = false)
    private String  siret;
    private boolean verified = false;

    @OneToMany(mappedBy = "organisateur")
    private List<Evenement> evenements;
    @OneToMany(mappedBy = "organisateur")
    private List<Promotion> promotions;



    public Organisateur(Long id, String email, String username, String motDePasse,
                        StatutCompte statutCompte, String adresse, String telephonePro, String nomOrganisation,
                        Long numRegistre, StatutOrganisateur statutOrganisateur, String nom, String prenom,
                        List<Evenement> evenements, List<Promotion> promotions, boolean varified, String siret) {

        super(id, username, nom, prenom, email, motDePasse, statutCompte, telephonePro, adresse);
        this.numRegistre = numRegistre;
        this.nomOrganisation = nomOrganisation;
        this.statutOrganisateur = statutOrganisateur;
        this.promotions = promotions;
        this.evenements = evenements;
        this.verified = varified;
        this.siret = siret;
    }

    public Organisateur(String email, String username, String motDePasse,
                        StatutCompte statutCompte, String adresse, String telephonePro, String nomOrganisation,
                        Long numRegistre, StatutOrganisateur statutOrganisateur, String nom, String prenom,
                        List<Evenement> evenements, List<Promotion> promotions, boolean varified, String siret) {
        super(username, nom, prenom, email, motDePasse, statutCompte, telephonePro, adresse);
        this.numRegistre = numRegistre;
        this.statutOrganisateur = statutOrganisateur;
        this.nomOrganisation = nomOrganisation;
        this.promotions = promotions;
        this.evenements = evenements;
        this.verified = varified;
        this.siret = siret;

    }

    public Organisateur(String email, String username, String motDePasse,
                        StatutCompte statutCompte, String adresse, String telephonePro,
                        StatutOrganisateur statutOrganisateur, String nom, String prenom,
                        String nomOrganisation, Long numRegistre, LocalDateTime dateCreation) {
        super(username, nom, prenom, email, motDePasse, statutCompte, telephonePro, adresse);
        this.numRegistre = numRegistre;
        this.nomOrganisation = nomOrganisation;
        this.statutOrganisateur = statutOrganisateur;
        this.dateValidation = dateCreation;
    }
}
