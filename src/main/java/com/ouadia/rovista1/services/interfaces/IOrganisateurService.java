package com.ouadia.rovista1.services.interfaces;


import com.ouadia.rovista1.dtos.OrganisateurDto;
import com.ouadia.rovista1.entities.Organisateur;

import java.util.List;
import java.util.Map;

public interface IOrganisateurService {
    public OrganisateurDto addOrganisateur(OrganisateurDto organisateurDto);
    public OrganisateurDto editOrganisateur(OrganisateurDto organisateurDto ,Long idRech);
    public OrganisateurDto editOrganisateurMap(Long idReche , Map<String,Object> map);
    public OrganisateurDto getOrganisateurById(Long id);
    public List<OrganisateurDto> getAllOrganisateurs();
    public void deleteOrganisateurById(Long id);
    public void deleteAllByIds(Long ... ids);
}
