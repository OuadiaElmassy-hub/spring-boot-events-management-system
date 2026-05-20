package com.ouadia.rovista1.services.interfaces;


import com.ouadia.rovista1.entities.Organisateur;

import java.util.List;

public interface IOrganisateurService {
    public Organisateur addOrganisateur(Organisateur organisateur);
    public Organisateur editOrganisateur(Organisateur organisateur);
    public Organisateur getOrganisateurById(Long id);
    public List<Organisateur> getAllOrganisateurs();
    public void deleteOrganisateurById(Long id);
}
