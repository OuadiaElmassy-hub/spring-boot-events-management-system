package com.pfe.backend.services.interfaces;


import com.pfe.backend.dtos.categorie.CategorieResponseDto;
import com.pfe.backend.exceptions.CategorieNotFoundException;

import java.util.List;

public interface ICategorieService {

    public CategorieResponseDto getCategorieById(Long id )throws  CategorieNotFoundException;
    public List<CategorieResponseDto> getAllCategories();
    public void deleteCategorieById( Long id);
    public void deleteAllByIds(Long ... ids);
}
