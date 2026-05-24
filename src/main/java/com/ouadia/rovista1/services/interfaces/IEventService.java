package com.ouadia.rovista1.services.interfaces;

import com.ouadia.rovista1.dtos.EvenementDto;
import com.ouadia.rovista1.dtos.EvenementDtoAdd;
import com.ouadia.rovista1.dtos.EvenementDtoAddIn;
import com.ouadia.rovista1.entities.Evenement;
import com.ouadia.rovista1.entities.enums.StatutEvenement;
import com.ouadia.rovista1.exceptions.EventNotFoundException;
import com.ouadia.rovista1.exceptions.StorageProblemException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface IEventService {

    public EvenementDto getEventById(Long id);
    public List<EvenementDto> getAllEvents();
    public List<EvenementDto> getEventsByStatut(StatutEvenement statut);
    public List<EvenementDto> searchEvents(String keyword);

    public EvenementDto addEvent(EvenementDtoAdd dto, MultipartFile imageFile, MultipartFile document) throws StorageProblemException;
    public EvenementDto addEvent(EvenementDtoAddIn dto) throws IOException, StorageProblemException; // dto avec les fichiers aussi

    public EvenementDto stockageDuDocument(Evenement evenement, MultipartFile document) throws StorageProblemException;
    public EvenementDto stockageDeLimage(EvenementDto evenementDto, MultipartFile image) throws StorageProblemException;

    public EvenementDto editEvent(Long id, EvenementDto evenementDto, MultipartFile imageFile, MultipartFile document) throws StorageProblemException, EventNotFoundException;

    public void deleteEventById(Long id) throws EventNotFoundException;
}
