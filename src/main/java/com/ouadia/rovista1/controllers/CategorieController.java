package com.ouadia.rovista1.controllers;

import com.ouadia.rovista1.dtos.categorie.CategorieRequestDto;
import com.ouadia.rovista1.dtos.categorie.CategorieResponseDto;
import com.ouadia.rovista1.exceptions.BusinessException;
import com.ouadia.rovista1.exceptions.CategorieNotFoundException;
import com.ouadia.rovista1.exceptions.StorageProblemException;
import com.ouadia.rovista1.services.interfaces.ICategorieService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CategorieController {

    final ICategorieService service;

    @GetMapping("/public/categories")
    public ResponseEntity<List<CategorieResponseDto>> getAllCategories(){
        return ResponseEntity.ok(service.getAllCategories());
    }

    @GetMapping("/public/categories/{id}")
    public ResponseEntity<CategorieResponseDto> getCategorieById(@PathVariable Long id) throws CategorieNotFoundException {
        return ResponseEntity.ok(service.getCategorieById(id));
    }

}
