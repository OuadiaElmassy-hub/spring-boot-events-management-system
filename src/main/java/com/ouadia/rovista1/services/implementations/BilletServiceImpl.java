package com.ouadia.rovista1.services.implementations;

import com.ouadia.rovista1.dtos.billet.BilletResponseDto;
import com.ouadia.rovista1.entities.Billet;
import com.ouadia.rovista1.exceptions.BilletNotFoundException;
import com.ouadia.rovista1.mappers.BilletMapper;
import com.ouadia.rovista1.repositories.BilletRepository;
import com.ouadia.rovista1.services.interfaces.IBilletService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class BilletServiceImpl implements IBilletService {

    private BilletRepository repository;
    private BilletMapper billetMapper;


    @Override
    public Billet addBillet(Billet billet) {
        return repository.save(billet);
    }

    @Override
    public Billet editBillet(Billet billet) {
        return repository.save(billet);
    }

    @Override
    public BilletResponseDto getBilletById(Long id) throws BilletNotFoundException {
        return billetMapper.mappingBilletToBilletDtoResponse(repository.findById(id).orElseThrow(()->new BilletNotFoundException("not found")));
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
