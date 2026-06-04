package com.ouadia.rovista1.services.implementations;

import com.ouadia.rovista1.dtos.PageResponse;
import com.ouadia.rovista1.dtos.evenement.EvenementRequestDto;
import com.ouadia.rovista1.dtos.evenement.EvenementResponseDto;
import com.ouadia.rovista1.dtos.evenement.UpdateEvenementRequestDto;
import com.ouadia.rovista1.entities.*;
import com.ouadia.rovista1.entities.enums.StatutEvenement;
import com.ouadia.rovista1.exceptions.*;
import com.ouadia.rovista1.mappers.EvenementMapper;
import com.ouadia.rovista1.repositories.*;
import com.ouadia.rovista1.services.EvenementSpecification;
import com.ouadia.rovista1.services.interfaces.IEventService;
import com.ouadia.rovista1.services.interfaces.IImageService;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class EventServiceImpl implements IEventService {

    private EventRepository repository;
    private CategorieRepository categorieRepository;
    private EvenementMapper evenementMapper;
    private OrganisateurRepository organisateurRepo;
    private IImageService imageService ;
    private AvisRepository avisRepository;
    private PromotionRepository promotionRepository;

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
                (() -> new OrganisateurNotFoundException("Organizer not found with id : " + organisateurId));
        evenement.setOrganisateur(org);

        Categorie c = categorieRepository.findById(dto.getCategorieId()).orElseThrow
                (() -> new CategorieNotFoundException(" categorie not found with id : " + dto.getCategorieId()));

        evenement.setCategorie(c);

        if(images != null)
            evenement = imageService.stockageDesImagesEvenement(evenement, images);

        evenement.setStatutEvenement(StatutEvenement.CREE);

        evenement.setPlacesRestants(evenement.getCapacite());

        Evenement saved = repository.save(evenement);
        return evenementMapper.mappingEvenementToEvenementDtoResponse(saved);
    }

    @Override
    public EvenementResponseDto updateEvenement(Long id, UpdateEvenementRequestDto dto) throws EventNotFoundException, BusinessException, PromotionNotFoundException, CategorieNotFoundException {

        Evenement existing = repository.findById(id).orElseThrow(() -> new EventNotFoundException("Event not found with id : " + id));

        if (dto.getDateFin().isBefore(dto.getDateDebut())) {
            throw new BusinessException("La date fin doit être après date début");
        }

        if (dto.getTitre() != null) existing.setTitre(dto.getTitre());
        if (dto.getLieuSpecifique() != null) existing.setLieuSpecifique(dto.getLieuSpecifique());
        if (dto.getVille() != null) existing.setVille(dto.getVille());
        if (dto.getDescription() != null) existing.setDescription(dto.getDescription());
        if (dto.getDateDebut() != null) existing.setDateDebut(dto.getDateDebut());
        if (dto.getDateFin() != null) existing.setDateFin(dto.getDateFin());
        if (dto.getCapacite() != 0) existing.setCapacite(dto.getCapacite());
        if (dto.getPrix() != 0) existing.setPrix(dto.getPrix());
        if (dto.getPlacesRestant() != 0) existing.setPlacesRestants(dto.getPlacesRestant());

        if (dto.getPromotionId() != 0) {
            existing.setPromotion(promotionRepository.findById(dto.getPromotionId()).orElseThrow
                    (() -> new PromotionNotFoundException("Promotion not found with id : " + dto.getPromotionId())));

        }
        if (dto.getCategorieId() != 0) {
            existing.setCategorie(categorieRepository.findById(dto.getCategorieId()).orElseThrow
                    (() -> new CategorieNotFoundException("Categorie not found with id : " + dto.getCategorieId())));

        }
        existing.setDateModification(LocalDateTime.now());

//        // Update image que si nouvelle image uploadée
//        if (imageFile != null && !imageFile.isEmpty()) {
//            existing = stockageDeLimage(existing, imageFile);
//        }

        return evenementMapper.mappingEvenementToEvenementDtoResponse(repository.save(existing));
    }

    @Override
    public EvenementResponseDto updateImagesEvenement(Long id, List<MultipartFile> images) throws EventNotFoundException, StorageProblemException {

        Evenement existing = repository.findById(id).orElseThrow(() -> new EventNotFoundException("Event not found with id : " + id));

        // Update image que si nouvelle image uploadée
        if (images != null && !images.isEmpty()) {
            existing = imageService.stockageDesImagesEvenement(existing, images);
        }

        return evenementMapper.mappingEvenementToEvenementDtoResponse(repository.save(existing));
    }

    @Override
    public void deleteEvenement(Long id) throws EventNotFoundException {
        Evenement existing = repository.findById(id).orElseThrow(() -> new EventNotFoundException("Event not found"));

        if (existing.getAvis() != null) {
            for (Avis a : existing.getAvis()) {
                avisRepository.deleteById(a.getId());
            }
        }
        if (existing.getImages() != null) {
            for (Image img : existing.getImages()) {
                imageService.deleteImage(img.getId());
            }
        }

        repository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public EvenementResponseDto getEvenementById(Long id) throws EventNotFoundException {
        return evenementMapper.mappingEvenementToEvenementDtoResponse(repository.findById(id).orElseThrow
                (() -> new EventNotFoundException("Event Not Found with id : " + id)));
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
//    public PageResponse<EvenementResponseDto> getAllEvents(int numPage, int size) {
//        Pageable pageable = PageRequest.of(numPage, size,
//                Sort.by("dateDebut").descending());
//        Page<Evenement> evenementPage = repository.findAll(pageable);
//
//        List<EvenementResponseDto> dtoList = new ArrayList<>();
//        for (Evenement evenement: evenementPage.getContent()){
//            EvenementResponseDto dto = evenementMapper.mappingEvenementToEvenementDtoResponse(evenement);
//            dtoList.add(dto);
//        }

//        PageResponse<EvenementResponseDto> response = new PageResponse<>();
//
//        response.setContent(dtoList);
//        response.setPage(evenementPage.getNumber());
//        response.setSize(evenementPage.getSize());
//        response.setTotalElements(evenementPage.getTotalElements());
//        response.setTotalPages(evenementPage.getTotalPages());
//
//        return response;
//     }

//        return new PageImpl<>(dtoList, pageable, evenementPage.getTotalElements());
//        // == avec lambda expressions: return evenementPage.map(evenementMapper::mappingEvenementToEvenementDtoResponse);


    @Override
    @Transactional(readOnly = true)
    public PageResponse<EvenementResponseDto> getAllPublishedEvenements(int numPage, int size) {

        Page<Evenement> evenementPage = repository.findByStatutEvenement(StatutEvenement.PUBLIE, PageRequest.of(numPage, size,
                Sort.by("dateDebut").descending()));
        List<EvenementResponseDto> content = evenementPage.getContent().stream()
                .map(evenementMapper::mappingEvenementToEvenementDtoResponse).toList();

        PageResponse<EvenementResponseDto> response = new PageResponse<>();

        response.setContent(content);
        response.setPage(evenementPage.getNumber());
        response.setSize(evenementPage.getSize());
        response.setTotalElements(evenementPage.getTotalElements());
        response.setTotalPages(evenementPage.getTotalPages());

        return response;
    }

    @Override
    @Transactional(readOnly = true)
    public PageResponse<EvenementResponseDto> getAllPublishedEvenementsForCategorie(Long categorieId, int numPage, int size) {

        Page<Evenement> evenementPage = repository.findByCategorieIdAndStatutEvenement(categorieId, StatutEvenement.PUBLIE,
                PageRequest.of(numPage, size, Sort.by("dateDebut").descending()));

        List<EvenementResponseDto> content = evenementPage.getContent().stream()
                .map(evenementMapper::mappingEvenementToEvenementDtoResponse).toList();

        PageResponse<EvenementResponseDto> response = new PageResponse<>();

        response.setContent(content);
        response.setPage(evenementPage.getNumber());
        response.setSize(evenementPage.getSize());
        response.setTotalElements(evenementPage.getTotalElements());
        response.setTotalPages(evenementPage.getTotalPages());

        return response;
    }

    @Override
    @Transactional(readOnly = true)
    public List<EvenementResponseDto> getEvenementsByOrganisateur(Long organisateurId) {
        List<Evenement> evenements = repository.findByOrganisateurId(organisateurId);
        return evenements.stream().map(evenementMapper::mappingEvenementToEvenementDtoResponse).toList() ;
    }

    @Override
    @Transactional(readOnly = true)
    public PageResponse<EvenementResponseDto> searchEvents(String ville, Long categorieId, LocalDate date, int numPage, int size) {

        Pageable pageable = PageRequest.of(numPage, size);
        Specification<Evenement> specification = EvenementSpecification.serch(ville, categorieId, date);
        Page<Evenement> evenementPage = repository.findAll(specification, pageable);

        List<EvenementResponseDto> dtoList = new ArrayList<>();
        for (Evenement evenement: evenementPage.getContent()){
            EvenementResponseDto dto = evenementMapper.mappingEvenementToEvenementDtoResponse(evenement);
            dtoList.add(dto);
        }

        PageResponse<EvenementResponseDto> response = new PageResponse<>();

        response.setContent(dtoList);
        response.setPage(evenementPage.getNumber());
        response.setSize(evenementPage.getSize());
        response.setTotalElements(evenementPage.getTotalElements());
        response.setTotalPages(evenementPage.getTotalPages());

        return response;

//        return new PageImpl<>(dtoList, pageable, evenementPage.getTotalElements());
//        //return evenementPage.map(evenementMapper::mappingEvenementToEvenementDtoResponse);
    }

    @Override
    public PageResponse<EvenementResponseDto> searchEvents(int page, int size, Long categorieId, String keyword, String ville, LocalDate date, Double prixMax) {
        Pageable pageable = PageRequest.of(page, size);
        Specification<Evenement> specification = EvenementSpecification.filter(
                categorieId,
                keyword,
                ville,
                date,
                prixMax
        );
        Page<Evenement> evenementPage = repository.findAll(specification, pageable);

        List<EvenementResponseDto> dtoList = new ArrayList<>();
        for (Evenement evenement: evenementPage.getContent()){
            EvenementResponseDto dto = evenementMapper.mappingEvenementToEvenementDtoResponse(evenement);
            dtoList.add(dto);
        }

        PageResponse<EvenementResponseDto> response = new PageResponse<>();

        response.setContent(dtoList);
        response.setPage(evenementPage.getNumber());
        response.setSize(evenementPage.getSize());
        response.setTotalElements(evenementPage.getTotalElements());
        response.setTotalPages(evenementPage.getTotalPages());

        return response;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Evenement> getEventsByStatut(StatutEvenement statut) {
        return repository.findByStatutEvenement(statut);
    }

    @Override
    public boolean demandeValidation(Long id) throws EventNotFoundException {

        Evenement evenement = repository.findById
                (id).orElseThrow
                (() -> new EventNotFoundException("Event not found with id : " + id));

        evenement.setStatutEvenement(StatutEvenement.EN_ATTENTE);
        return(repository.save(evenement).getStatutEvenement().equals(StatutEvenement.EN_ATTENTE));
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
