package com.ouadia.rovista1.entities;

import com.ouadia.rovista1.entities.enums.StatutCompte;
import com.ouadia.rovista1.entities.enums.StatutOrganisateur;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;

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
    private String logoUrl;
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
                        StatutCompte statutCompte, String adresse, String telephonePro,
                        String nomOrganisation, Long numRegistre, StatutOrganisateur statutOrganisateur,
                        List<Evenement> evenements, List<Promotion> promotions, boolean varified, String siret) {

        super(id, username, nomOrganisation, email, motDePasse, statutCompte, telephonePro, adresse);
        this.numRegistre = numRegistre;
        this.statutOrganisateur = statutOrganisateur;
        this.promotions = promotions;
        this.evenements = evenements;
        this.verified = varified;
        this.siret = siret;
    }

    public Organisateur(String email, String username, String motDePasse,
                        StatutCompte statutCompte, String adresse, String telephonePro,
                        String nomOrganisation, Long numRegistre, StatutOrganisateur statutOrganisateur,
                        List<Evenement> evenements, List<Promotion> promotions, boolean varified, String siret) {
        super(username, email, nomOrganisation, motDePasse, statutCompte, telephonePro, adresse);
        this.numRegistre = numRegistre;
        this.statutOrganisateur = statutOrganisateur;
        this.promotions = promotions;
        this.evenements = evenements;
        this.verified = varified;
        this.siret = siret;

    }

    public Organisateur(String email, String username, String motDePasse,
                        StatutCompte statutCompte, String adresse, String telephonePro,
                        StatutOrganisateur statutOrganisateur,
                        String nomOrganisation, Long numRegistre,
                        LocalDateTime dateCreation, String logoUrl) {
        super(username, email, nomOrganisation, motDePasse, statutCompte, telephonePro, adresse);
        this.numRegistre = numRegistre;
        this.statutOrganisateur = statutOrganisateur;
        this.dateValidation = dateCreation;
        this.logoUrl = logoUrl;
    }
}
