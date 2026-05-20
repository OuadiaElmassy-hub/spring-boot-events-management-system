package com.ouadia.rovista1.services.implementations;

import com.ouadia.rovista1.entities.Categorie;
import com.ouadia.rovista1.repositories.CategorieRepository;
import com.ouadia.rovista1.services.interfaces.ICategorieService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class CategorieServiceImpl implements ICategorieService {

    private CategorieRepository repository;


    @Override
    public Categorie addCategorie(Categorie categorie) {
        return repository.save(categorie);
    }

    @Override
    public Categorie editCategorie(Categorie categorie) {
        return repository.save(categorie);
    }

    @Override
    public Categorie getCategorieById(Long id) {
        return repository.findById(id).orElseThrow();
    }

    @Override
    public List<Categorie> getAllCategories() {
        return repository.findAll();
    }

    @Override
    public void deleteCategorieById(Long id) {
        repository.deleteById(id);
    }
}
