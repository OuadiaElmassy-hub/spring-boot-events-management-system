package com.ouadia.rovista1.services.implementations;

import com.ouadia.rovista1.entities.Utilisateur;
import com.ouadia.rovista1.repositories.UtilisateurRepository;
import com.ouadia.rovista1.services.IUtilisateurService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class UtilisateurServiceImpl implements IUtilisateurService {

    private UtilisateurRepository repository;

    public UtilisateurServiceImpl(UtilisateurRepository repository) {
        this.repository = repository;
    }

    @Override
    public Utilisateur addUtilisateur(Utilisateur utilisateur) {
        return repository.save(utilisateur);
    }

    @Override
    public Utilisateur editUtilisateur(Utilisateur utilisateur) {
        return repository.save(utilisateur);
    }

    @Override
    public Utilisateur getUtilisateurById(Long id) {
        return repository.findById(id).orElseThrow();
    }

    @Override
    public List<Utilisateur> getAllUtilisateurs() {
        return repository.findAll();
    }

    @Override
    public void deleteUtilisateurById(Long id) {
        repository.deleteById(id);
    }
}
