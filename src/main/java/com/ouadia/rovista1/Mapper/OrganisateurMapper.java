package com.ouadia.rovista1.Mapper;

import com.ouadia.rovista1.dtos.organisateur.OrganisateurRequestDto;
import com.ouadia.rovista1.dtos.organisateur.OrganisateurResponseDto;
import com.ouadia.rovista1.entities.Organisateur;

public class OrganisateurMapper {
    public static OrganisateurResponseDto mapToOrganisateurDto(Organisateur organisateur){

        new OrganisateurResponseDto();
        return OrganisateurResponseDto.builder()
                .id(organisateur.getId())
                .username(organisateur.getUsername())
                .email(organisateur.getEmail())
                .phone(organisateur.getPhone())
                .motDePasse(organisateur.getMotDePasse())
                .adresse(organisateur.getAdresse())
                .statutCompte(organisateur.getStatutCompte())
                .statutOrganisateur(organisateur.getStatutOrganisateur())
                .numRegistre(organisateur.getNumRegistre())
                .nomOrganisation(organisateur.getNomOrganisation())
                .build();
    }

    public static Organisateur mapToOrganisateur(OrganisateurRequestDto dto){

        return new Organisateur(
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
