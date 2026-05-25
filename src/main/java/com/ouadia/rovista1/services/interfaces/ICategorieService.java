package com.ouadia.rovista1.services.interfaces;


import com.ouadia.rovista1.dtos.CategorieDto;
import com.ouadia.rovista1.entities.Categorie;
import com.ouadia.rovista1.exceptions.CategorieNotFoundException;

import java.util.List;
import java.util.Map;

public interface ICategorieService {
    public CategorieDto addCategorie(CategorieDto categorieDto);
    public CategorieDto editCategorie(CategorieDto categorieDto ,  Integer id);
    public CategorieDto editCategorieMap(  Integer id , Map<String,Object> map);
    public CategorieDto getCategorieById(  Integer id )throws  CategorieNotFoundException;
    public List<CategorieDto> getAllCategories();
    public void deleteCategorieById( Integer id);
    public void deleteAllByIds(Integer ... ids);
}
