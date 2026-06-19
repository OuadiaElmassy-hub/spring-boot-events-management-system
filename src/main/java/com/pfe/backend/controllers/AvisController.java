package com.pfe.backend.controllers;

import com.pfe.backend.dtos.PageResponse;
import com.pfe.backend.dtos.avis.AvisRequestDto;
import com.pfe.backend.dtos.avis.AvisResponseDto;
import com.pfe.backend.exceptions.AvisNotFoundException;
import com.pfe.backend.exceptions.EventNotFoundException;
import com.pfe.backend.services.interfaces.IAvisService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class AvisController {

    final IAvisService service;

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
