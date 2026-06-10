package com.ouadia.rovista1.controllers;

import com.ouadia.rovista1.dtos.PageResponse;
import com.ouadia.rovista1.dtos.avis.AvisRequestDto;
import com.ouadia.rovista1.dtos.avis.AvisResponseDto;
import com.ouadia.rovista1.exceptions.BusinessException;
import com.ouadia.rovista1.exceptions.AvisNotFoundException;
import com.ouadia.rovista1.exceptions.EventNotFoundException;
import com.ouadia.rovista1.exceptions.StorageProblemException;
import com.ouadia.rovista1.services.interfaces.IAvisService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
@CrossOrigin("*")
public class AvisController {

    IAvisService service;

    @PostMapping("public/avis")
    public ResponseEntity<AvisResponseDto> createAvis(@RequestPart AvisRequestDto avisDto) {
        return ResponseEntity.ok(service.addAvis(avisDto));
    }

    // un evenement parmi les events publies

    @GetMapping("/public/events/{id}/avis")
    public ResponseEntity<PageResponse<AvisResponseDto>> getListAvisByEvenementId(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "5") int size,
            @PathVariable Long id) throws EventNotFoundException {
        return ResponseEntity.ok(service.getListAvisByEvenementId(page, size, id));
    }

    @GetMapping("/public/avis/{id}")
    public ResponseEntity<AvisResponseDto> getAvisById(@PathVariable Long id) throws AvisNotFoundException {
        return ResponseEntity.ok(service.getAvisById(id));
    }

    @DeleteMapping("/admin/avis/{id}")
    public ResponseEntity<Void> deleteAvisById(@PathVariable Long id) throws AvisNotFoundException {
        service.deleteAvisById(id);
        return ResponseEntity.noContent().build();
    }
}
