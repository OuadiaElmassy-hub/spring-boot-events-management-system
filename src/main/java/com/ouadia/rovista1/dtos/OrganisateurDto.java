package com.ouadia.rovista1.dtos;

import com.ouadia.rovista1.entities.Utilisateur;
import com.ouadia.rovista1.entities.enums.StatutCompte;
import com.ouadia.rovista1.entities.enums.StatutOrganisateur;

public class OrganisateurDto extends Utilisateur {

    private String nomOrganisation;
    private int numRegistre;
    private StatutOrganisateur statutOrganisateur;

    public OrganisateurDto() {}

    public OrganisateurDto(Long id, String email, String username, String motDePasse,
                        StatutCompte statutCompte, String adresse, String telephonePro,
                        String nomOrganisation, int numRegistre, StatutOrganisateur statutOrganisateur) {
        super(id, username, email, motDePasse, statutCompte, telephonePro, adresse);
        this.numRegistre = numRegistre;
        this.statutOrganisateur = statutOrganisateur;
        this.nomOrganisation = nomOrganisation;
    }

    public int getNumRegistre() {
        return numRegistre;
    }

    public void setNumRegistre(int numRegistre) {
        this.numRegistre = numRegistre;
    }

    public String getNomOrganisation() {
        return nomOrganisation;
    }

    public void setNomOrganisation(String nomOrganisation) {
        this.nomOrganisation = nomOrganisation;
    }

    public StatutOrganisateur getStatutOrganisateur() { return statutOrganisateur; }
    public void setStatutOrganisateur(StatutOrganisateur statutOrganisateur) { this.statutOrganisateur = statutOrganisateur; }
}
