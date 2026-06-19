package com.pfe.backend.controllers;

import com.pfe.backend.dtos.PageResponse;
import com.pfe.backend.dtos.VilleResponseDto;
import com.pfe.backend.dtos.evenement.EvenementResponseDto;
import com.pfe.backend.exceptions.*;
import com.pfe.backend.exceptions.EventNotFoundException;
import com.pfe.backend.exceptions.StorageProblemException;
import com.pfe.backend.services.interfaces.IEventService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class EventController {

    private final IEventService service;

    @GetMapping("/public/events/search/{id}")
    public ResponseEntity<EvenementResponseDto> getPublishedEventById(@PathVariable Long id) throws EventNotFoundException {
        return ResponseEntity.ok(service.getPublishedEvenementById(id));
    }

    @GetMapping("/public/villes")
    public ResponseEntity<List<VilleResponseDto>> getVilles(){
        return ResponseEntity.ok(service.getVilles());
    }

    @GetMapping("/public/events/search")
    public ResponseEntity<PageResponse<EvenementResponseDto>> searchEvents(
            @RequestParam(required = false) String ville,
            @RequestParam(required = false) Double prixMax,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) Long categorieId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "9") int size) {

        return ResponseEntity.ok(service.searchEvents(page, size,
                categorieId,
                keyword,
                ville,
                date,
                prixMax));
    }

    @GetMapping("/public/events")
    public ResponseEntity<PageResponse<EvenementResponseDto>> getPublishedEvents(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "8") int size){
        return ResponseEntity.ok(service.getAllPublishedEvenements(page, size));
    }

    @GetMapping("/public/events/categorie")
    public ResponseEntity<PageResponse<EvenementResponseDto>> getPublishedEventsForCategory(
            @RequestParam(name = "numPage", defaultValue = "0") int numPage,
            @RequestParam(name = "size", defaultValue = "8") int size,
            @RequestParam(name = "categorieId", defaultValue = "1") Long categorieId){

        return ResponseEntity.ok(service.getAllPublishedEvenementsForCategorie(categorieId, numPage, size));
    }

    @PutMapping("/organisateur/events/{id}/images")
    public ResponseEntity<EvenementResponseDto> updateImagesEvenement(
            @PathVariable Long id,
            @RequestParam("images") List<MultipartFile> images) throws EventNotFoundException, StorageProblemException {
        return ResponseEntity.ok(service.updateImagesEvenement(id, images));
    }

    @PutMapping("/organisateur/events/{id}/demmande")
    public ResponseEntity<Boolean> demandeValidation(@PathVariable Long id) throws EventNotFoundException {

        return ResponseEntity.ok(service.demandeValidation(id));
    }

}