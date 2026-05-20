package com.ouadia.rovista1.services.implementations;

import com.ouadia.rovista1.entities.Avis;
import com.ouadia.rovista1.repositories.AvisRepository;
import com.ouadia.rovista1.services.interfaces.IAvisService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class AvisServiceImpl implements IAvisService {

    private AvisRepository repository;



    @Override
    public Avis addAvis(Avis avis) {
        return repository.save(avis);
    }

    @Override
    public Avis editAvis(Avis avis) {
        return repository.save(avis);
    }

    @Override
    public Avis getAvisById(Long id) {
        return repository.findById(id).orElseThrow();
    }

    @Override
    public List<Avis> getAllAviss() {
        return repository.findAll();
    }

    @Override
    public void deleteAvisById(Long id) {
        repository.deleteById(id);
    }
}
