package com.ouadia.rovista1.entities;

import com.ouadia.rovista1.entities.enums.StatutCompte;
import com.ouadia.rovista1.entities.enums.StatutOrganisateur;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;
@Entity
@Data // pour les methode getter, setter, toString() , hachcode() ,equals()
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Organisateur extends Utilisateur{
    @Column(nullable = false)
    private String nomOrganisation;
    @Column(nullable = false)
    private int numRegistre;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private StatutOrganisateur statutOrganisateur;

    @OneToMany(mappedBy = "organisateur")
    private List<Evenement> evenements;
    @OneToMany(mappedBy = "organisateur")
    private List<Promotion> promotions;



    public Organisateur(Long id, String email, String username, String motDePasse,
                        StatutCompte statutCompte, String adresse, String telephonePro,
                        String nomOrganisation, int numRegistre, StatutOrganisateur statutOrganisateur,
                        List<Evenement> evenements, List<Promotion> promotions) {
        super(id, username, email, motDePasse, statutCompte, telephonePro, adresse);
        this.numRegistre = numRegistre;
        this.statutOrganisateur = statutOrganisateur;
        this.nomOrganisation = nomOrganisation;
        this.promotions = promotions;
    }


}
