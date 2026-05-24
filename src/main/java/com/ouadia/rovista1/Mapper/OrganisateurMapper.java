package com.ouadia.rovista1.Mapper;

import com.ouadia.rovista1.dtos.OrganisateurDto;
import com.ouadia.rovista1.entities.Organisateur;

public class OrganisateurMapper {
    public static OrganisateurDto mapToOrganisateurDto(Organisateur organisateur){

        return new OrganisateurDto(
                organisateur.getId(),
                organisateur.getUsername(),
                organisateur.getEmail(),
                organisateur.getPhone(),
                organisateur.getMotDePasse(),
                organisateur.getAdresse(),
                organisateur.getStatutCompte(),
                organisateur.getNomOrganisation(),
                organisateur.getNumRegistre(),
                organisateur.getStatutOrganisateur()
        );
    }

    public static Organisateur mapToOrganisateur(OrganisateurDto dto){

        return new Organisateur(
                dto.getId(),
                dto.getUsername(),
                dto.getEmail(),
                dto.getMotDePasse(),
                dto.getStatutCompte(),
                dto.getPhone(),
                dto.getAdresse(),
                dto.getNomOrganisation(),
                dto.getNumRegistre(),
                dto.getStatutOrganisateur(),
                null,
                null
        );
    }
}
