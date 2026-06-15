package com.ouadia.rovista1.services.implementations;

import com.ouadia.rovista1.mappers.CategorieMapper;
import com.ouadia.rovista1.dtos.categorie.CategorieRequestDto;
import com.ouadia.rovista1.dtos.categorie.CategorieResponseDto;
import com.ouadia.rovista1.entities.*;
import com.ouadia.rovista1.entities.Categorie;
import com.ouadia.rovista1.exceptions.BusinessException;
import com.ouadia.rovista1.exceptions.CategorieNotFoundException;
import com.ouadia.rovista1.exceptions.StorageProblemException;
import com.ouadia.rovista1.repositories.CategorieRepository;
import com.ouadia.rovista1.services.interfaces.ICategorieService;
import com.ouadia.rovista1.services.interfaces.IImageService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

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
