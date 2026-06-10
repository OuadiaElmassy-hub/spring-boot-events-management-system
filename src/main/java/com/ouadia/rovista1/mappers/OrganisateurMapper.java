package com.ouadia.rovista1.mappers;

import com.ouadia.rovista1.dtos.organisateur.OrganisateurRequestDto;
import com.ouadia.rovista1.dtos.organisateur.OrganisateurResponseDto;
import com.ouadia.rovista1.entities.Organisateur;
import org.springframework.stereotype.Component;

@Component
public class OrganisateurMapper {
    public OrganisateurResponseDto mapToOrganisateurDto(Organisateur organisateur){

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
                .nomOrganisation(organisateur.getNom())
                .build();
    }

    public Organisateur mapToOrganisateur(OrganisateurRequestDto dto){

        return Organisateur.builder()
                .username(dto.getUsername())
                .email(dto.getEmail())
                .motDePasse(dto.getMotDePasse())
                .statutCompte(dto.getStatutCompte())
                .phone(dto.getPhone())
                .adresse(dto.getAdresse())
                .nom(dto.getNomOrganisation())
                .numRegistre(dto.getNumRegistre())
                .statutOrganisateur(dto.getStatutOrganisateur())
                .build();
    }
}
