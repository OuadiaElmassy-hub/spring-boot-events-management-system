package com.ouadia.rovista1.services.interfaces;


import com.ouadia.rovista1.dtos.categorie.CategorieRequestDto;
import com.ouadia.rovista1.dtos.categorie.CategorieResponseDto;
import com.ouadia.rovista1.exceptions.CategorieNotFoundException;

import java.util.List;
import java.util.Map;

public interface ICategorieService {
    public CategorieResponseDto addCategorie(CategorieRequestDto categorieDto);
    public CategorieResponseDto editCategorie(CategorieRequestDto categorieDto ,  Long id);
    public CategorieResponseDto editCategorieMap(  Long id , Map<String,Object> map);
    public CategorieResponseDto getCategorieById(  Long id )throws  CategorieNotFoundException;
    public List<CategorieResponseDto> getAllCategories();
    public void deleteCategorieById( Long id);
    public void deleteAllByIds(Long ... ids);
}
