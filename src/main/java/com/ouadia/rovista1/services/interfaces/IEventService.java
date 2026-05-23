package com.ouadia.rovista1.services.interfaces;

import com.ouadia.rovista1.dtos.evenement.EvenementRequestDto;
import com.ouadia.rovista1.dtos.evenement.EvenementResponseDto;
import com.ouadia.rovista1.entities.Evenement;
import com.ouadia.rovista1.entities.enums.StatutEvenement;
import com.ouadia.rovista1.exceptions.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
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

    Evenement stockageDesImages(Evenement evenement, List<MultipartFile> images) throws StorageProblemException;

    EvenementResponseDto updateEvenement(
            Long id,
            EvenementRequestDto dto
    ) throws EventNotFoundException, BusinessException;

    void deleteEvenement(Long id) throws EventNotFoundException;

    EvenementResponseDto getEvenementById(Long id) throws EventNotFoundException;

    //Page<EvenementResponseDto> getAllEvents(int numPage, int size);

    List<Evenement> getEventsByStatut(StatutEvenement statut);

    Page<EvenementResponseDto> getAllPublishedEvenements(int numPage, int size);

    List<EvenementResponseDto> getEvenementsByOrganisateur( Long organisateurId);

    Page<EvenementResponseDto> searchEvents(String ville, Long categorieId, LocalDate date, int numPage, int size);

    void validAndPublieEvent(Long id) throws EventNotFoundException;
    void rejeterEvent(Long id) throws EventNotFoundException;

    //____________________________________________

}
