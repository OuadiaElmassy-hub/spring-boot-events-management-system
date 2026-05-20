package com.ouadia.rovista1.services.implementations;

import com.ouadia.rovista1.entities.Organisateur;
import com.ouadia.rovista1.repositories.OrganisateurRepository;
import com.ouadia.rovista1.services.interfaces.IOrganisateurService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class OrganisateurServiceImpl implements IOrganisateurService {

    private OrganisateurRepository repository;


    @Override
    public Organisateur addOrganisateur(Organisateur organisateur) {
        return repository.save(organisateur);
    }

    @Override
    public Organisateur editOrganisateur(Organisateur organisateur) {
        return repository.save(organisateur);
    }

    @Override
    public Organisateur getOrganisateurById(Long id) {
        return repository.findById(id).orElseThrow();
    }

    @Override
    public List<Organisateur> getAllOrganisateurs() {
        return repository.findAll();
    }

    @Override
    public void deleteOrganisateurById(Long id) {
        repository.deleteById(id);
    }
}
