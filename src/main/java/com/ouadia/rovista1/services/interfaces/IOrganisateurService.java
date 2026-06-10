package com.ouadia.rovista1.services.interfaces;

import com.ouadia.rovista1.dtos.organisateur.OrganisateurRequestDto;
import com.ouadia.rovista1.dtos.organisateur.OrganisateurResponseDto;
import com.ouadia.rovista1.entities.Organisateur;
import com.ouadia.rovista1.exceptions.BusinessException;
import com.ouadia.rovista1.exceptions.OrganisateurNotFoundException;

import java.util.List;
import java.util.Map;

public interface IOrganisateurService {
    OrganisateurResponseDto addOrganisateur(OrganisateurRequestDto organisateurRequestDto) throws BusinessException;
    OrganisateurResponseDto editOrganisateur(OrganisateurRequestDto organisateurRequestDto, Long idReche) throws BusinessException;
    OrganisateurResponseDto editOrganisateurMap(Long idReche , Map<String,Object> map) throws BusinessException;
    OrganisateurResponseDto getOrganisateurById(Long id) throws OrganisateurNotFoundException;
    Organisateur getOrganisateurEntityById(Long id);
    List<OrganisateurResponseDto> getAllOrganisateurs();
    void deleteOrganisateurById(Long id);
    void deleteAllByIds(Long ... ids);
}
