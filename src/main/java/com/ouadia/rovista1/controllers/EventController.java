package com.ouadia.rovista1.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ouadia.rovista1.dtos.EvenementDtoAdd;
import com.ouadia.rovista1.dtos.EvenementDtoAddIn;
import com.ouadia.rovista1.entities.Evenement;
import com.ouadia.rovista1.entities.enums.Categorie;
import com.ouadia.rovista1.entities.enums.StatutEvenement;
import com.ouadia.rovista1.exceptions.EventNotFoundException;
import com.ouadia.rovista1.exceptions.StorageProblemException;
import com.ouadia.rovista1.services.IEventService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/events")
public class EventController {

    private IEventService service;
    private ObjectMapper mapper;

    public EventController(IEventService service, ObjectMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @GetMapping("/{id}")
    public Evenement getEventById(@PathVariable Long id){
        return service.getEventById(id);
    }

    @GetMapping
    public List<Evenement> getAllEvents(){
        return service.getAllEvents();
    }

    @GetMapping("/published")
    public List<Evenement> getPublishedEvents(){
        return service.getEventsByStatut(StatutEvenement.PUBLIE);
    }

    @GetMapping("/{eventId}/image")
    public ResponseEntity<byte[]> getEventImage(@PathVariable Long eventId) {
        Evenement evenement = service.getEventById(eventId);
        if (evenement == null || evenement.getImageUri() == null) {
            return ResponseEntity.notFound().build(); // 404
        }
        Path path = Paths.get(evenement.getImageUri());
        if (!Files.exists(path)){
            return ResponseEntity.notFound().build(); // 404
        }
        try {
            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(Files.probeContentType(path)))
                    .body(Files.readAllBytes(path)); // 200 + image bytes
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @GetMapping("/{eventId}/document")
    public ResponseEntity<byte[]> getEventDocument(@PathVariable Long eventId) {
        Evenement evenement = service.getEventById(eventId);
        if (evenement == null || evenement.getFichierUri() == null) {
            return ResponseEntity.notFound().build(); // 404
        }
        Path path = Paths.get(evenement.getFichierUri());
        if (!Files.exists(path)) {
            return ResponseEntity.notFound().build(); // 404
        }
        try {
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_PDF)
                    //.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"document.pdf\"") pour le telechargement automatique.
                    .body(Files.readAllBytes(path)); // 200 + image bytes
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/search/{keyword}")
    public ResponseEntity<List<Evenement>> searchEvents(@PathVariable String keyword) {
        List<Evenement> evenements = service.searchEvents(keyword);
        return new ResponseEntity<>(evenements, HttpStatus.OK); // 200 + list of evenements
    }
//****************************************** POST **********************************************************************

    //solution final
    @PostMapping(path = "/addAvecDto", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> addEvent(@RequestPart("dto") String dtoJson,
                                      @RequestPart("imageFile") MultipartFile imageFile,
                                      @RequestPart("document") MultipartFile document) {
        try {
            EvenementDtoAdd dto = mapper.readValue(dtoJson, EvenementDtoAdd.class);
            Evenement savedEvent = service.addEvent(dto, imageFile, document);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedEvent); // 201 Created + body
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erreur upload image: " + e.getMessage()); // 500 + message
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage()); // 400 + message
        }
    }

    @PostMapping(path = "/addDtoIn", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> addAllEvent(@ModelAttribute EvenementDtoAddIn dto) {
        try {
            Evenement savedEvent = service.addEvent(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedEvent); // 201 Created + body
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erreur upload image: " + e.getMessage()); // 500 + message
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage()); // 400 + message
        }
    }

    // deuxieme façon upload au meme temp pour probleme swagger
    // on peut mettre les composantes de notre event dans les parametres de la methode puis on construit un event

    @PostMapping(path = "/add", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> addEvent(@RequestParam String titre, @RequestParam String description,
                                      @RequestParam String lieu, @RequestParam Categorie categorie,
                                      @RequestParam int capacite, @RequestParam double prix,
                                      @RequestParam LocalDate date, @RequestParam StatutEvenement statut,
                                      @RequestPart("imageFile") MultipartFile imageFile,
                                      @RequestPart("document") MultipartFile document) {
        EvenementDtoAdd dto = new EvenementDtoAdd
                (titre, description, date, lieu, categorie, capacite, prix, statut);//params
        try {
            Evenement savedEvent = service.addEvent(dto, imageFile, document);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedEvent); // 201 Created + body
        } catch (StorageProblemException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erreur upload image: " + e.getMessage()); // 500 + message
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage()); // 400 + message
        }
    }

//****************************************** PUT **********************************************************************

    @PutMapping(path = "/edit/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> editEvent(@PathVariable Long id,
                                       @RequestPart("evenement") Evenement evenement,
                                       @RequestPart("imageFile") MultipartFile imageFile,
                                       @RequestPart("document") MultipartFile document) {
        try {
            Evenement updated = service.editEvent(id, evenement, imageFile, document);
            return ResponseEntity.ok(updated); // ou ("Updated", HttpStatus.OK) 200 OK
        } catch (EventNotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage()); // 400
        } catch (StorageProblemException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage()); // 500
        }
//        Evenement updated = null;
//        try {
//            updated = service.editEvent(id, evenement, imageFile);
//        } catch (IOException e) {
//            return new ResponseEntity<>("Failed to update", HttpStatus.BAD_REQUEST); // 500
//        }
    }
//****************************************** DELETE *******************************************************************

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteEventById(@PathVariable Long id) {
        try {
            service.deleteEventById(id);
            return new ResponseEntity<>("Deleted", HttpStatus.OK); // 204 No Content ou HttpStatus.NO_CONTENT
        } catch (EventNotFoundException e) {
            return new ResponseEntity<>("Event not found", HttpStatus.NOT_FOUND); // 404
        }
    }
}