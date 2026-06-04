package com.ouadia.rovista1.services.implementations;

import com.ouadia.rovista1.Mapper.CategorieMapper;
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
    private IImageService imageService;


    @Override
    public CategorieResponseDto addCategorie(CategorieRequestDto categorieDto, MultipartFile image) throws BusinessException, StorageProblemException {

        Categorie categorie = categorieMapper.mapToCategorie(categorieDto);

        if (repository.findByNom(categorie.getNom()) != null) {
            throw new BusinessException(" Categorie exsist ");
        }

        categorie = imageService.stockageDesImagesCategorie(categorie, image);
        return categorieMapper.mapToDto (repository.save(categorie));
    }

    @Override
    public  CategorieResponseDto editCategorie(CategorieRequestDto categorieDto, Long id) throws CategorieNotFoundException {
        Categorie categorie = categorieMapper.mapToCategorie(categorieDto);

        Categorie categorie1 = repository.findById(id).orElseThrow(() -> new CategorieNotFoundException("Categorie No found withe id : "+ id));

        categorie1.setNom(categorie.getNom());
        categorie1.setDescription(categorie.getDescription());
        categorie1.setEvenements(categorie.getEvenements());
        return categorieMapper.mapToDto (repository.save(categorie1));
    }

    @Override
    public CategorieResponseDto editCategorieMap(Long id, Map<String, Object> map) throws CategorieNotFoundException {
        if (map==null){return null;}
        Categorie categorie1 = repository.findById(id).orElseThrow(() -> new CategorieNotFoundException("Categorie No found withe id : "+ id));
        if (map.containsKey("nom")) {
            categorie1.setNom((String) map.get("nom"));
        }
        if (map.containsKey("description")) {
            categorie1.setDescription((String) map.get("description"));
        }
        if (map.containsKey("evenements")) {
            categorie1.setEvenements((List<Evenement>)map.get("evenements"));
        }


        return categorieMapper.mapToDto (repository.save(categorie1));
    }

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
