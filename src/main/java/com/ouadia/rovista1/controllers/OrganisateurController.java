package com.ouadia.rovista1.controllers;

import com.ouadia.rovista1.dtos.organisateur.OrganisateurRequestDto;
import com.ouadia.rovista1.dtos.organisateur.OrganisateurResponseDto;
import com.ouadia.rovista1.exceptions.BusinessException;
import com.ouadia.rovista1.services.interfaces.IOrganisateurService;
import lombok.AllArgsConstructor;
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
