package com.ouadia.rovista1.services.implementations;

import com.ouadia.rovista1.Mapper.BilletMapper;
import com.ouadia.rovista1.Mapper.CategorieMapper;
import com.ouadia.rovista1.Mapper.CategorieMapper;
import com.ouadia.rovista1.dtos.BilletDto;
import com.ouadia.rovista1.dtos.CategorieDto;
import com.ouadia.rovista1.entities.*;
import com.ouadia.rovista1.entities.Categorie;
import com.ouadia.rovista1.entities.enums.TypeBillet;
import com.ouadia.rovista1.exceptions.CategorieNotFoundException;
import com.ouadia.rovista1.repositories.CategorieRepository;
import com.ouadia.rovista1.services.interfaces.ICategorieService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
@Transactional
@AllArgsConstructor
public class CategorieServiceImpl implements ICategorieService {

    private CategorieRepository repository;


    @Override
    public CategorieDto addCategorie(CategorieDto categorieDto) {
        Categorie categorie = CategorieMapper.mapToCategorie(categorieDto);
        if (repository.findById(categorie.getId()).isPresent()) {
            throw new RuntimeException(" billet exsist ");
        } else
            return CategorieMapper.mapToCategorieDto (repository.save(categorie));
    }

    @Override
    public CategorieDto editCategorie(CategorieDto categorieDto,Integer id) {
        Categorie categorie = CategorieMapper.mapToCategorie(categorieDto);
        if (categorie == null) return null;
        else {
            Categorie categorie1 = repository.findById(id).get();
            if (categorie1 == null) {
                return null;
            }
            categorie1.setNom(categorie.getNom());
            categorie1.setDescription(categorie.getDescription());
            categorie1.setEvenements(categorie.getEvenements());
            return CategorieMapper.mapToCategorieDto (repository.save(categorie1));
        }
    }

    @Override
    public CategorieDto editCategorieMap(Integer id, Map<String, Object> map) {
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


        return CategorieMapper.mapToCategorieDto (repository.save(categorie1));
    }

    @Override
    public CategorieDto getCategorieById(Integer id) throws CategorieNotFoundException {
        return CategorieMapper.mapToCategorieDto( repository.findById(id).
                orElseThrow(()->new CategorieNotFoundException("categorie not found")));
    }

    @Override
    public List<CategorieDto> getAllCategories() {
            return repository.findAll().stream().map(categorie-> CategorieMapper.mapToCategorieDto(categorie)).toList();

        }

    @Override
    public void deleteCategorieById(Integer id) {
        repository.deleteById(id);
    }

    @Override
    public void deleteAllByIds(Integer... ids) {
        for (Integer id :ids){
            deleteCategorieById(id);
        }
    }
}
