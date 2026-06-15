package com.ouadia.rovista1.services.admin;

import com.ouadia.rovista1.dtos.PageResponse;
import com.ouadia.rovista1.dtos.admin.AdminCategorieDTO;
import com.ouadia.rovista1.dtos.categorie.CategorieResponseDto;
import com.ouadia.rovista1.dtos.evenement.EvenementResponseDto;
import com.ouadia.rovista1.exceptions.BusinessException;
import com.ouadia.rovista1.exceptions.CategorieNotFoundException;
import com.ouadia.rovista1.mappers.CategorieMapper;
import com.ouadia.rovista1.repositories.CategorieRepository;
import com.ouadia.rovista1.services.EvenementSpecification;
import com.ouadia.rovista1.services.FileStorageService;
import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.ouadia.rovista1.entities.*;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;


@Service
@Transactional
@AllArgsConstructor
public class AdminCategorieService {

    private final CategorieRepository repository;
    private final CategorieMapper categorieMapper;
    private final FileStorageService fileStorageService;

    public AdminCategorieDTO createCategorie(
            String  nom,
            String description,
            String couleur,
            MultipartFile icone) throws Exception {

        if (repository.existsByNomIgnoreCase(nom))
            return null;

        String iconUrl = null;
        if (icone != null && !icone.isEmpty())
            iconUrl = fileStorageService.store(icone, "categories");

        Categorie cat = Categorie.builder()
                .nom(nom).description(description)
                .couleur(couleur).iconUrl(iconUrl)
                .active(true).build();

        return categorieMapper.mapToDtoAdmin(repository.save(cat));
    }

    public AdminCategorieDTO updateCategorie(
            Long id,
            String  nom,
            String description,
            String couleur,
            MultipartFile icone) throws Exception {

        return repository.findById(id).map(cat -> {
            cat.setNom(nom);
            if (description != null) cat.setDescription(description);
            if (couleur     != null) cat.setCouleur(couleur);
            try {
                if (icone != null && !icone.isEmpty()) {
                    // Supprimer l'ancienne icône locale si elle existe
                    fileStorageService.delete(cat.getIconUrl());
                    cat.setIconUrl(fileStorageService.store(icone, "categories"));
                }
            } catch (Exception e) {
                throw new RuntimeException("Erreur upload icône", e);
            }
            return categorieMapper.mapToDtoAdmin(repository.save(cat));
        }).orElseThrow(() -> new CategorieNotFoundException("Categorie not found with id : "+id));
    }

    public void toggleCategorie(Long id) throws CategorieNotFoundException {

        Categorie cat = repository.findById(id).orElseThrow(() -> new
                CategorieNotFoundException("Categorie not found with id : "+id));
        cat.setActive(!Boolean.TRUE.equals(cat.getActive()));
        repository.save(cat);
    }

    public void deleteCategorie(Long id) throws CategorieNotFoundException, BusinessException {

        Categorie cat = repository.findById(id).orElseThrow(() -> new
                CategorieNotFoundException("Categorie not found with id : "+id));

        if (repository.countEvenements(id) > 0)
            throw new BusinessException("Can not delete categorie because of countEvents!= o");

        fileStorageService.delete(cat.getIconUrl());
        repository.delete(cat);
    }

    public Page<AdminCategorieDTO> searchCategories(String search, int page, int size, String sort) {

        String[] s = sort.split(",");
        Pageable pageable = PageRequest.of(page, size,
                Sort.by("asc".equalsIgnoreCase(s.length > 1 ? s[1] : "asc")
                        ? Sort.Direction.ASC : Sort.Direction.DESC, s[0]));

        Page<Categorie> categoriePage = repository.
                searchCategories(search.isBlank() ? null : search, pageable);

//        return categoriePage.map(categorie -> {
//            AdminCategorieDTO dto = categorieMapper.mapToDtoAdmin(categorie);
//
//            Long count = repository.countEvenements(categorie.getId());
//            dto.setTotalEvents(count);
//
//            return dto;
//        });

        // Récupérer tous les ids de la page
        List<Long> ids = categoriePage.stream().map(Categorie::getId).toList();

        // Charger les counts en une seule requête
        Map<Long, Long> counts = repository.countEvenementsByCategorieIds(ids)
                .stream()
                .collect(Collectors.toMap(
                        row -> (Long) row[0],
                        row -> (Long) row[1]
                ));

        // Mapper + enrichir
        return categoriePage.map(categorie -> {
            AdminCategorieDTO dto = categorieMapper.mapToDtoAdmin(categorie);
            dto.setTotalEvents(counts.getOrDefault(categorie.getId(), 0L));
            return dto;
        });

//        List<AdminCategorieDTO> dtoList = new ArrayList<>();
//        for (Categorie categorie : categoriePage.getContent()){
//            AdminCategorieDTO dto = categorieMapper.mapToDtoAdmin(categorie);
//            dtoList.add(dto);
//        }
//
//        PageResponse<AdminCategorieDTO> response = new PageResponse<>();
//
//        response.setContent(dtoList);
//        response.setPage(categoriePage.getNumber());
//        response.setSize(categoriePage.getSize());
//        response.setTotalElements(categoriePage.getTotalElements());
//        response.setTotalPages(categoriePage.getTotalPages());
//
//        return response;
    }

    public List<CategorieResponseDto> getAllCategories() {
        return repository.findAll().stream().map(categorieMapper::mapToDto).toList();

    }
}
