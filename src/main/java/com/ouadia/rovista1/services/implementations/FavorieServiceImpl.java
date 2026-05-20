package com.ouadia.rovista1.services.implementations;

import com.ouadia.rovista1.entities.Favorie;
import com.ouadia.rovista1.repositories.FavorieRepository;
import com.ouadia.rovista1.services.interfaces.IFavorieService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class FavorieServiceImpl implements IFavorieService {

    private FavorieRepository repository;



    @Override
    public Favorie addFavorie(Favorie favorie) {
        return repository.save(favorie);
    }

    @Override
    public Favorie editFavorie(Favorie favorie) {
        return repository.save(favorie);
    }

    @Override
    public Favorie getFavorieById(Long id) {
        return repository.findById(id).orElseThrow();
    }

    @Override
    public List<Favorie> getAllFavories() {
        return repository.findAll();
    }

    @Override
    public void deleteFavorieById(Long id) {
        repository.deleteById(id);
    }
}
