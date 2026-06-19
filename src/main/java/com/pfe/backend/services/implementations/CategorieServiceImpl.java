package com.pfe.backend.services.implementations;

import com.pfe.backend.mappers.CategorieMapper;
import com.pfe.backend.dtos.categorie.CategorieResponseDto;
import com.pfe.backend.exceptions.CategorieNotFoundException;
import com.pfe.backend.repositories.CategorieRepository;
import com.pfe.backend.services.interfaces.ICategorieService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class CategorieServiceImpl implements ICategorieService {

    private CategorieRepository repository;
    private CategorieMapper categorieMapper;

    @Override
    public CategorieResponseDto getCategorieById(Long id) throws CategorieNotFoundException {
        return categorieMapper.mapToDto( repository.findById(id).
                orElseThrow(()->new CategorieNotFoundException("categorie not found")));
    }

    @Override
    public List<CategorieResponseDto> getAllCategories() {
            return repository.findAll().stream().map(categorieMapper::mapToDto).toList();

    }

    @Override
    public void deleteCategorieById(Long id) {
        repository.deleteById(id);
    }

    @Override
    public void deleteAllByIds(Long... ids) {
        for (Long id :ids){
            deleteCategorieById(id);
        }
    }
}
