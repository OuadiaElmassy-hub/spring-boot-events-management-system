package com.pfe.backend.controllers;

import com.pfe.backend.dtos.organisateur.OrganisateurRequestDto;
import com.pfe.backend.dtos.organisateur.OrganisateurResponseDto;
import com.pfe.backend.exceptions.BusinessException;
import com.pfe.backend.services.interfaces.IOrganisateurService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/organisateur")
@RequiredArgsConstructor
public class OrganisateurController {

    final IOrganisateurService service;

    @PostMapping
    public OrganisateurResponseDto createOrganisateur(OrganisateurRequestDto organisateurRequestDto) throws BusinessException {
        return service.addOrganisateur(organisateurRequestDto);
    }
}
