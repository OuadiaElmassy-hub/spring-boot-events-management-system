package com.ouadia.rovista1.services.implementations;

import com.ouadia.rovista1.entities.Billet;
import com.ouadia.rovista1.repositories.BilletRepository;
import com.ouadia.rovista1.services.IBilletService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class BilletServiceImpl implements IBilletService {

    private BilletRepository repository;

    public BilletServiceImpl(BilletRepository repository) {
        this.repository = repository;
    }

    @Override
    public Billet addBillet(Billet billet) {
        return repository.save(billet);
    }

    @Override
    public Billet editBillet(Billet billet) {
        return repository.save(billet);
    }

    @Override
    public Billet getBilletById(Long id) {
        return repository.findById(id).orElseThrow();
    }

    @Override
    public List<Billet> getAllBillets() {
        return repository.findAll();
    }

    @Override
    public void deleteBilletById(Long id) {
        repository.deleteById(id);
    }
}
