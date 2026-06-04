package com.ouadia.rovista1.services.implementations;

import com.ouadia.rovista1.dtos.categorie.CategorieRequestDto;
import com.ouadia.rovista1.dtos.categorie.CategorieResponseDto;
import com.ouadia.rovista1.entities.*;
import com.ouadia.rovista1.entities.Categorie;
import com.ouadia.rovista1.exceptions.CategorieNotFoundException;
import com.ouadia.rovista1.mappers.CategorieMapper;
import com.ouadia.rovista1.repositories.CategorieRepository;
import com.ouadia.rovista1.services.interfaces.ICategorieService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@Transactional
@AllArgsConstructor
public class CategorieServiceImpl implements ICategorieService {

    private CategorieRepository repository;



    @Override
    public CategorieResponseDto addCategorie(CategorieRequestDto categorieDto) {
        Categorie categorie = CategorieMapper.mappingCategorieDtoRequestToCategorie(categorieDto);

            return CategorieMapper.mappingCategorieToCategorieDtoResponse (repository.save(categorie));
    }

    @Override
    public CategorieResponseDto editCategorie(CategorieRequestDto categorieDto,Long id) {
        Categorie categorie = CategorieMapper.mappingCategorieDtoRequestToCategorie(categorieDto);
        if (categorie == null) return null;
        else {
            Categorie categorie1 = repository.findById(id).get();
            if (categorie1 == null) {
                return null;
            }
            categorie1.setNom(categorie.getNom());
            categorie1.setDescription(categorie.getDescription());
            categorie1.setEvenements(categorie.getEvenements());
            return CategorieMapper.mappingCategorieToCategorieDtoResponse (repository.save(categorie1));
        }
    }

    @Override
    public CategorieResponseDto editCategorieMap(Long id, Map<String, Object> map) {
        if (map==null){return null;}
        Categorie categorie1 = repository.findById(id).get();
        if (categorie1 == null) {
            return null;
        }
        if (map.containsKey("nom")) {
            categorie1.setNom((String) map.get("nom"));
        }
        if (map.containsKey("description")) {
            categorie1.setDescription((String) map.get("description"));
        }
        if (map.containsKey("evenements")) {
            categorie1.setEvenements((List<Evenement>)map.get("evenements"));
        }


        return CategorieMapper.mappingCategorieToCategorieDtoResponse (repository.save(categorie1));
    }

    @Override
    public CategorieResponseDto getCategorieById(Long id) throws CategorieNotFoundException {
        return CategorieMapper.mappingCategorieToCategorieDtoResponse( repository.findById(id).
                orElseThrow(()->new CategorieNotFoundException("categorie not found")));
    }

    @Override
    public List<CategorieResponseDto> getAllCategories() {
            return repository.findAll().stream().map(categorie-> CategorieMapper.mappingCategorieToCategorieDtoResponse(categorie)).toList();

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
