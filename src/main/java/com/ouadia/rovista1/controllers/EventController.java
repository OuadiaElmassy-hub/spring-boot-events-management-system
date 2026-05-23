package com.ouadia.rovista1.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ouadia.rovista1.dtos.evenement.EvenementRequestDto;
import com.ouadia.rovista1.dtos.evenement.EvenementResponseDto;
import com.ouadia.rovista1.exceptions.*;
import com.ouadia.rovista1.services.interfaces.IEventService;
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
@CrossOrigin
@RequestMapping("/api/events")
@RequiredArgsConstructor
public class EventController {

    private final IEventService service;
    private ObjectMapper mapper;

    @PostMapping(path = "/organisateur", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<EvenementResponseDto> addEvent(@RequestPart("evenement")
                                                         @Valid EvenementRequestDto dto,
                                                         @RequestPart("imagesFiles")
                                                         List<MultipartFile> imagesFiles,
                                                         @RequestParam Long organisateurId) throws BusinessException,
            CategorieNotFoundException, StorageProblemException, OrganisateurNotFoundException {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(service.createEvenement(dto, imagesFiles, organisateurId));
    }

    // version 2 pour swagger
//    @PostMapping(path = "/organisateur", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
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


    @GetMapping("/{id}")
    public ResponseEntity<EvenementResponseDto> getEventById(@PathVariable Long id) throws EventNotFoundException {
        return ResponseEntity.ok(service.getEvenementById(id));
    }

    @GetMapping("/search?{numPage}")
    public ResponseEntity<Page<EvenementResponseDto>> searchEvents(
            @RequestParam(required = false)
            String ville,
            @RequestParam(required = false)
            Long categorieId,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate date,
            @PathVariable int numPage) {
        return ResponseEntity.ok(service.searchEvents(ville, categorieId, date, numPage, 5));
    }

    @GetMapping("/public?{numPage}")
    public Page<EvenementResponseDto> getPublishedEvents(@PathVariable int numPage){
        return service.getAllPublishedEvenements(numPage, 5);
    }

    @GetMapping("/{id}/organisateur")
    public EvenementResponseDto updateEvenement(@PathVariable Long id,
                                                @RequestPart EvenementRequestDto dto) throws BusinessException, EventNotFoundException {
        return service.updateEvenement(id, dto);
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteEventById(@PathVariable Long id) throws EventNotFoundException {
        service.deleteEvenement(id);
        return ResponseEntity.noContent().build();
    }
}