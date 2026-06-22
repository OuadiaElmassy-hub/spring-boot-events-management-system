package com.pfe.backend.controllers;

import com.pfe.backend.dtos.categorie.CategorieResponseDto;
import com.pfe.backend.exceptions.CategorieNotFoundException;
import com.pfe.backend.services.interfaces.ICategorieService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
