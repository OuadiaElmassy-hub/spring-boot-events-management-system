package com.pfe.backend.services.interfaces;

import com.pfe.backend.dtos.organisateur.OrganisateurRequestDto;
import com.pfe.backend.dtos.organisateur.OrganisateurResponseDto;
import com.pfe.backend.entities.Organisateur;
import com.pfe.backend.exceptions.BusinessException;
import com.pfe.backend.exceptions.OrganisateurNotFoundException;

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
