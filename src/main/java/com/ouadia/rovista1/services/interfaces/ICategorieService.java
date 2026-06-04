package com.ouadia.rovista1.services.interfaces;


import com.ouadia.rovista1.dtos.categorie.CategorieRequestDto;
import com.ouadia.rovista1.dtos.categorie.CategorieResponseDto;
import com.ouadia.rovista1.exceptions.BusinessException;
import com.ouadia.rovista1.exceptions.CategorieNotFoundException;
import com.ouadia.rovista1.exceptions.StorageProblemException;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface ICategorieService {

    CategorieResponseDto addCategorie(CategorieRequestDto categorieDto, MultipartFile image) throws BusinessException, StorageProblemException;

    public CategorieResponseDto editCategorie(CategorieRequestDto categorieDto , Long id) throws CategorieNotFoundException;
    public CategorieResponseDto editCategorieMap(Long id , Map<String,Object> map) throws CategorieNotFoundException;
    public CategorieResponseDto getCategorieById(Long id )throws  CategorieNotFoundException;
    public List<CategorieResponseDto> getAllCategories();
    public void deleteCategorieById( Long id);
    public void deleteAllByIds(Long ... ids);
}
