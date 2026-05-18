package com.ouadia.rovista1.services.implementations;

import com.ouadia.rovista1.services.IPaiementService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import com.ouadia.rovista1.entities.Paiement;
import com.ouadia.rovista1.repositories.PaiementRepository;

import java.util.List;

@Service
@Transactional
public class PaiementServiceImpl implements IPaiementService {

    private PaiementRepository repository;

    public PaiementServiceImpl(PaiementRepository repository) {
        this.repository = repository;
    }

    @Override
    public Paiement addPaiement(Paiement paiement) {
        return repository.save(paiement);
    }

    @Override
    public Paiement editPaiement(Paiement paiement) {
        return repository.save(paiement);
    }

    @Override
    public Paiement getPaiementById(Long id) {
        return repository.findById(id).orElseThrow();
    }

    @Override
    public List<Paiement> getAllPaiements() {
        return repository.findAll();
    }

    @Override
    public void deletePaiementById(Long id) {
        repository.deleteById(id);
    }
}
