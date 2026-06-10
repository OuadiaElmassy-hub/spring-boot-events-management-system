package com.ouadia.rovista1.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ouadia.rovista1.dtos.PageResponse;
import com.ouadia.rovista1.dtos.evenement.EvenementRequestDto;
import com.ouadia.rovista1.dtos.evenement.EvenementResponseDto;
import com.ouadia.rovista1.dtos.evenement.UpdateEvenementRequestDto;
import com.ouadia.rovista1.entities.Evenement;
import com.ouadia.rovista1.entities.enums.StatutEvenement;
import com.ouadia.rovista1.exceptions.*;
import com.ouadia.rovista1.services.interfaces.IEventService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/api")
@RequiredArgsConstructor
public class EventController {

    private final IEventService service;
    private ObjectMapper mapper;

    // on doit separer les end points de chque type de user !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

    @PostMapping(path = "/organisateur/events", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<EvenementResponseDto> addEvent(@RequestPart
                                                         @Valid @Parameter(content = @Content(mediaType = "application/json")) EvenementRequestDto dto,
                                                         @RequestPart("imagesFiles")
                                                         List<MultipartFile> imagesFiles,
                                                         @RequestParam Long organisateurId) throws BusinessException,
            CategorieNotFoundException, StorageProblemException, OrganisateurNotFoundException {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(service.createEvenement(dto, imagesFiles, organisateurId));
    }

    // version 2 pour swagger

//    @PostMapping(path = "/organisateur/events", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//    public ResponseEntity<EvenementResponseDto> addEventJson(@RequestPart("dto") String dtoJson,
//                                                         @RequestPart("imageFile")
//                                                         List<MultipartFile> imagesFiles,
//                                                         @RequestParam Long organisateurId) throws BusinessException,
//            CategorieNotFoundException, StorageProblemException, OrganisateurNotFoundException, JsonProcessingException {
//
//        EvenementRequestDto dto = mapper.readValue(dtoJson, EvenementRequestDto.class);
//        return ResponseEntity.status(HttpStatus.CREATED)
//                .body(service.createEvenement(dto, imagesFiles, organisateurId)); // 201 Created + body
//    }

// pour l'admin
    @GetMapping("/admin/events/{id}")
    public ResponseEntity<EvenementResponseDto> getEventById(@PathVariable Long id) throws EventNotFoundException {
        return ResponseEntity.ok(service.getEvenementById(id));
    }

    @GetMapping("/public/events/search/{id}")
    public ResponseEntity<EvenementResponseDto> getPublishedEventById(@PathVariable Long id) throws EventNotFoundException {
        return ResponseEntity.ok(service.getPublishedEvenementById(id));
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
            @RequestParam(defaultValue = "8") int size) {

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


    //@PutMapping("/organisateur/events/{id}")
    public ResponseEntity<EvenementResponseDto> updateEvenement(@PathVariable Long id,
                                                @RequestPart UpdateEvenementRequestDto dto)
            throws BusinessException, EventNotFoundException, PromotionNotFoundException, CategorieNotFoundException {
        return ResponseEntity.ok(service.updateEvenement(id, dto));
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

    //@DeleteMapping("/organisateur/events/{id}")
    public ResponseEntity<String> deleteEventById(@PathVariable Long id) throws EventNotFoundException {
        service.deleteEvenement(id);
        return ResponseEntity.noContent().build();
    }
}