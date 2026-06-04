package com.ouadia.rovista1.services.implementations;

import com.ouadia.rovista1.dtos.evenement.EvenementRequestDto;
import com.ouadia.rovista1.dtos.evenement.EvenementResponseDto;
import com.ouadia.rovista1.entities.*;
import com.ouadia.rovista1.entities.enums.StatutEvenement;
import com.ouadia.rovista1.exceptions.*;
import com.ouadia.rovista1.mappers.EvenementMapper;
import com.ouadia.rovista1.repositories.*;
import com.ouadia.rovista1.services.EvenementSpecification;
import com.ouadia.rovista1.services.interfaces.IEventService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.transaction.annotation.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class EventServiceImpl implements IEventService {

    private EventRepository repository;
    private CategorieRepository categorieRepository;
    private EvenementMapper evenementMapper;
    private OrganisateurRepository organisateurRepo;
    private ImageRepository imageRepository;
    private AvisRepository avisRepository;

    // version 2

    @Override
    public EvenementResponseDto createEvenement(EvenementRequestDto dto, List<MultipartFile> images, Long organisateurId)
            throws OrganisateurNotFoundException, StorageProblemException, CategorieNotFoundException, BusinessException {

        if (dto.getDateFin().isBefore(dto.getDateDebut())) {
            throw new BusinessException("La date fin doit être après date début");
        }

        Evenement evenement = evenementMapper.mappingEvenementDtoRequestToEvenement(dto);

        Organisateur org = organisateurRepo.findById
                (organisateurId).orElseThrow
                (()-> new OrganisateurNotFoundException("Organizer not found with id : "+ organisateurId));
        evenement.setOrganisateur(org);

        Categorie c = categorieRepository.findById(dto.getCategorieId()).orElseThrow
                (()-> new CategorieNotFoundException(" categorie not found with id : "+ dto.getCategorieId()));

        evenement.setCategorie(c);

        evenement = stockageDesImages(evenement, images);

        evenement.setStatutEvenement(StatutEvenement.EN_ATTENTE);

        Evenement saved = repository.save(evenement);
        return evenementMapper.mappingEvenementToEvenementDtoResponse(saved);
    }

    @Override
    public EvenementResponseDto updateEvenement(Long id, EvenementRequestDto dto) throws EventNotFoundException, BusinessException {

        Evenement existing = repository.findById(id).orElseThrow(() -> new EventNotFoundException("Event not found with id : "+ id));

        if (dto.getDateFin().isBefore(dto.getDateDebut())) {
            throw new BusinessException("La date fin doit être après date début");
        }

        existing.setTitre(dto.getTitre());
        existing.setLieuSpecifique(dto.getLieuSpecifique());
        existing.setVille(dto.getVille());
        existing.setDescription(dto.getDescription());
        existing.setDateDebut(dto.getDateDebut());
        existing.setDateFin(dto.getDateFin());
        existing.setHeureDebut(dto.getHeureDebut());
        existing.setCapacite(dto.getCapacite());
        existing.setPrix(dto.getPrix());

//        // Update image que si nouvelle image uploadée
//        if (imageFile != null && !imageFile.isEmpty()) {
//            existing = stockageDeLimage(existing, imageFile);
//        }

        return evenementMapper.mappingEvenementToEvenementDtoResponse(repository.save(existing));
    }

    @Override
    public void deleteEvenement(Long id) throws EventNotFoundException {
        Evenement existing = repository.findById(id).orElseThrow(() -> new EventNotFoundException("Event not found"));

        if (existing.getAvis() != null){
            for (Avis a : existing.getAvis()){
                avisRepository.deleteById(a.getId());
            }
        }
        if (existing.getImages() != null){
            for (Image img : existing.getImages()){
                imageRepository.deleteById(img.getId());
            }
        }

        repository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public EvenementResponseDto getEvenementById(Long id) throws EventNotFoundException {
        return evenementMapper.mappingEvenementToEvenementDtoResponse(repository.findById(id).orElseThrow
                (()-> new EventNotFoundException("Event Not Found with id : "+ id)));
    }

    @Override
    public Evenement getEvenementEntityById(Long id) throws EventNotFoundException {
        return repository.findById(id).orElseThrow
                (()-> new EventNotFoundException("Event Not Found with id : "+ id));
    }

    // get all events
    // get seulement les events valide
    // get seulement les events d'un organisateurs

//    @Override
//    public Page<EvenementResponseDto> getAllEvents(int numPage, int size) {
//        Pageable pageable = PageRequest.of(numPage, size,
//                Sort.by("dateDebut").descending());
//        Page<Evenement> evenementPage = repository.findAll(pageable);
//
//        List<EvenementResponseDto> dtoList = new ArrayList<>();
//        for (Evenement evenement: evenementPage.getContent()){
//            EvenementResponseDto dto = evenementMapper.mappingEvenementToEvenementDtoResponse(evenement);
//            dtoList.add(dto);
//        }
//        return new PageImpl<>(dtoList, pageable, evenementPage.getTotalElements());
//        // == avec lambda expressions: return evenementPage.map(evenementMapper::mappingEvenementToEvenementDtoResponse);
//    }

    @Override
    @Transactional(readOnly = true)
    public Page<EvenementResponseDto> getAllPublishedEvenements(int numPage, int size) {
        return repository.findByStatutEvenement(StatutEvenement.PUBLIE, PageRequest.of(numPage, size,
                Sort.by("dateDebut").descending()));
    }

    @Override
    @Transactional(readOnly = true)
    public List<EvenementResponseDto> getEvenementsByOrganisateur(Long organisateurId) {
        List<Evenement> evenements = repository.findByOrganisateurId(organisateurId);
        return evenements.stream().map(evenementMapper::mappingEvenementToEvenementDtoResponse).toList() ;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<EvenementResponseDto> searchEvents(String ville, Long categorieId, LocalDate date, int numPage, int size) {

        Pageable pageable = PageRequest.of(numPage, size);
        Specification<Evenement> specification = EvenementSpecification.serch(ville, categorieId, date);
        Page<Evenement> evenementPage = repository.findAll(specification, pageable);

        List<EvenementResponseDto> dtoList = new ArrayList<>();
        for (Evenement evenement: evenementPage.getContent()){
            EvenementResponseDto dto = evenementMapper.mappingEvenementToEvenementDtoResponse(evenement);
            dtoList.add(dto);
        }
        return new PageImpl<>(dtoList, pageable, evenementPage.getTotalElements());
        //return evenementPage.map(evenementMapper::mappingEvenementToEvenementDtoResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Evenement> getEventsByStatut(StatutEvenement statut) {
        return repository.findByStatutEvenement(statut);
    }

    @Override
    public Evenement stockageDesImages(Evenement evenement, List<MultipartFile> images) throws StorageProblemException {
        // stockage des fichiers dans notre machine :
        Path imagesFolderPath = Paths.get(System.getProperty("user.home"), "spring-pfe-data", "events-images", "event_num_"+evenement.getId());// chemin de l'image
        try{
            if (!Files.exists(imagesFolderPath)){ // si'il n'exist pas on va le cree
                Files.createDirectories(imagesFolderPath);
            }
            for (MultipartFile imgFile : images) {
                String imageName = UUID.randomUUID().toString() + "_" + imgFile.getOriginalFilename();
                Path imagePath = Paths.get(System.getProperty("user.home"), "spring-pfe-data", "events-images", "event_num_"+evenement.getId(), imageName); //+".png"
                Files.copy(imgFile.getInputStream(), imagePath);

                new Image();
                Image img = imageRepository.save(
                        Image.builder()
                                .nom(imageName)
                                .url(imagePath.toString())
                                .evenement(evenement)
                                .build());

                evenement.getImages().add(img); // stockage du img dans event list
            }
        } catch (IOException e) {
            throw new StorageProblemException(e.getMessage());
        }
        return evenement;
    }

    @Override
    public void validAndPublieEvent(Long id) throws EventNotFoundException {
        Evenement evenement = repository.findById(id).orElseThrow
                (()-> new EventNotFoundException("evenement introuvable avec id : "+id));

        evenement.setStatutEvenement(StatutEvenement.PUBLIE);
        repository.save(evenement);
    }

    @Override
    public void rejeterEvent(Long id) throws EventNotFoundException {
        Evenement evenement = repository.findById(id).orElseThrow
                (()-> new EventNotFoundException("evenement introuvable avec id : "+id));

        evenement.setStatutEvenement(StatutEvenement.REJETE);
        repository.save(evenement);
    }

    // methode d'annulation
    //____________________________________________

}
