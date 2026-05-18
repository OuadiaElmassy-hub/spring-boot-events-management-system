package com.ouadia.rovista1.services.implementations;

import com.ouadia.rovista1.dtos.EvenementDtoAdd;
import com.ouadia.rovista1.dtos.EvenementDtoAddIn;
import com.ouadia.rovista1.entities.Evenement;
import com.ouadia.rovista1.entities.enums.StatutEvenement;
import com.ouadia.rovista1.exceptions.EventNotFoundException;
import com.ouadia.rovista1.exceptions.StorageProblemException;
import com.ouadia.rovista1.repositories.CategorieRepository;
import com.ouadia.rovista1.repositories.EventRepository;
import com.ouadia.rovista1.services.IEventService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class EventServiceImpl implements IEventService {
    private EventRepository repository;
    private CategorieRepository categorieRepository;

    public EventServiceImpl(EventRepository repository, CategorieRepository categorieRepository) {
        this.repository = repository;
        this.categorieRepository = categorieRepository;
    }

    @Override
    public Evenement getEventById(Long id){
        return repository.findById(id).orElse(null);
    }

    @Override
    public List<Evenement> getAllEvents(){
        return repository.findAll();
    }

    @Override
    public List<Evenement> getEventsByStatut(StatutEvenement statut) {
        return repository.findByStatutEvenement(statut);
    }

    @Override
    public List<Evenement> searchEvents(String keyword) {
        return repository.searchEvents(keyword);
    }

    @Override
    public Evenement addEvent(EvenementDtoAddIn dto) throws IOException, StorageProblemException {
        Evenement evenement = new Evenement();
        evenement.setTitre(dto.getTitre());
        evenement.setLieuSpecifique(dto.getLieu());
        evenement.setPrix(dto.getPrix());
        evenement.setStatutEvenement(dto.getStatut());
        evenement.setDateDebut(dto.getDate());
        evenement.setCategorie(categorieRepository.findByNom(dto.getCategorie().toString()));
        evenement.setDescription(dto.getDescription());
        evenement.setCapacite(dto.getCapacite());
        evenement = stockageDuDocument(evenement, dto.getDocument());
        stockageDeLimage(evenement, dto.getImage());
        return repository.save(evenement);
    }

    @Override
    public Evenement stockageDuDocument(Evenement evenement, MultipartFile document) throws StorageProblemException {
        Path documentFolderPath = Paths.get(System.getProperty("user.home"), "spring-pfe-data", "events-documents");// chemin du document
        try {
            if (!Files.exists(documentFolderPath)){ // si'il n'exist pas on va le cree
                Files.createDirectories(documentFolderPath);
            }
            String fileName = UUID.randomUUID().toString()+"_"+document.getOriginalFilename(); // nom unique
            Path filePath = Paths.get(System.getProperty("user.home"), "spring-pfe-data", "events-documents", fileName+".pdf");
            Files.copy(document.getInputStream(), filePath);
            evenement.setFichierUri(filePath.toUri().toString()); // stockage du chemin dans event attribut
        } catch (IOException e) {
            throw new StorageProblemException(e.getMessage());
        }
        return evenement;
    }

    @Override
    public Evenement stockageDeLimage(Evenement evenement, MultipartFile image) throws StorageProblemException {
        // stockage des fichiers dans notre machine :
        Path imageFolderPath = Paths.get(System.getProperty("user.home"), "spring-pfe-data", "events-images");// chemin de l'image
        try{
            if (!Files.exists(imageFolderPath)){ // si'il n'exist pas on va le cree
                Files.createDirectories(imageFolderPath);
            }
            String imageName = UUID.randomUUID().toString()+"_"+image.getOriginalFilename();
            Path imagePath = Paths.get(System.getProperty("user.home"), "spring-pfe-data", "events-images", imageName); //+".png"
            Files.copy(image.getInputStream(), imagePath);
            evenement.setImageUri(imagePath.toUri().toString()); // stockage du chemin dans event attribut
        } catch (IOException e) {
            throw new StorageProblemException(e.getMessage());
        }
        return evenement;
    }

    @Override
    public Evenement addEvent(EvenementDtoAdd dto, MultipartFile imageFile, MultipartFile document) throws StorageProblemException {
        Evenement evenement = new Evenement();
        evenement.setTitre(dto.getTitre());
        evenement.setLieuSpecifique(dto.getLieu());
        evenement.setPrix(dto.getPrix());
        evenement.setStatutEvenement(dto.getStatut());
        evenement.setDateDebut(dto.getDate());
        evenement.setCategorie(categorieRepository.findByNom(dto.getCategorie().toString()));
        evenement.setDescription(dto.getDescription());
        evenement.setCapacite(dto.getCapacite());

        evenement = stockageDuDocument(evenement, document);
        evenement = stockageDeLimage(evenement, imageFile);
        return repository.save(evenement);
    }


    @Override
    public Evenement editEvent(Long id, Evenement evenement, MultipartFile imageFile, MultipartFile document) throws StorageProblemException, EventNotFoundException {

        Evenement existing = repository.findById(id).orElseThrow(() -> new EventNotFoundException("Event not found"));
        existing.setLieuSpecifique(evenement.getLieuSpecifique());
        existing.setDescription(evenement.getDescription());
        existing.setDateDebut(evenement.getDateDebut());
        existing.setPrix(evenement.getPrix());
        existing.setCategorie(evenement.getCategorie());
        existing.setCapacite(evenement.getCapacite());
        existing.setStatutEvenement(evenement.getStatutEvenement());
        existing.setTitre(evenement.getTitre());

        // Update image que si nouvelle image uploadée
        if (imageFile != null && !imageFile.isEmpty()) {
            existing = stockageDeLimage(existing, imageFile);
        }
        // Update document que si nouvelle document uploadée
        if (document != null && !document.isEmpty()) {
            existing = stockageDuDocument(existing, document);
        }
        return repository.save(existing);
    }


    @Override
    public void deleteEventById(Long id) throws EventNotFoundException {
        Evenement existing = repository.findById(id).orElseThrow(() -> new EventNotFoundException("Event not found"));
        repository.deleteById(id);
    }
}
