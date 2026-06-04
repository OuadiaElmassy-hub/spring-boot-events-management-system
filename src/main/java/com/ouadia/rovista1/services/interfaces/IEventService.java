package com.ouadia.rovista1.services.interfaces;

import com.ouadia.rovista1.dtos.PageResponse;
import com.ouadia.rovista1.dtos.evenement.EvenementRequestDto;
import com.ouadia.rovista1.dtos.evenement.EvenementResponseDto;
import com.ouadia.rovista1.dtos.evenement.UpdateEvenementRequestDto;
import com.ouadia.rovista1.entities.Evenement;
import com.ouadia.rovista1.entities.enums.StatutEvenement;
import com.ouadia.rovista1.exceptions.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

public interface IEventService {

    // version 2
    EvenementResponseDto createEvenement(
            EvenementRequestDto dto,
            List<MultipartFile> images,
            Long organisateurId
    ) throws OrganisateurNotFoundException, StorageProblemException, CategorieNotFoundException, BusinessException;

    EvenementResponseDto updateEvenement(Long id, UpdateEvenementRequestDto dto)
            throws EventNotFoundException, BusinessException, PromotionNotFoundException, CategorieNotFoundException;

    EvenementResponseDto updateImagesEvenement(Long id, List<MultipartFile> images) throws EventNotFoundException, StorageProblemException;

    void deleteEvenement(Long id) throws EventNotFoundException;

    EvenementResponseDto getEvenementById(Long id) throws EventNotFoundException;

    //PageResponse<EvenementResponseDto> getAllEvents(int numPage, int size);

    List<Evenement> getEventsByStatut(StatutEvenement statut);

    PageResponse<EvenementResponseDto> getAllPublishedEvenements(int numPage, int size);

    PageResponse<EvenementResponseDto> getAllPublishedEvenementsForCategorie( Long categorieId, int numPage, int size);


    List<EvenementResponseDto> getEvenementsByOrganisateur( Long organisateurId);

    PageResponse<EvenementResponseDto> searchEvents(String ville, Long categorieId, LocalDate date, int numPage, int size);
    PageResponse<EvenementResponseDto> searchEvents(int page, int size, Long categorieId, String keyword, String ville, LocalDate date, Double prixMax);

    boolean demandeValidation(Long id) throws EventNotFoundException;

    void validAndPublieEvent(Long id) throws EventNotFoundException;
    void rejeterEvent(Long id) throws EventNotFoundException;


    //____________________________________________

}
