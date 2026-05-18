package com.ouadia.rovista1.services;

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

    public Evenement getEventById(Long id);
    public List<Evenement> getAllEvents();
    public List<Evenement> getEventsByStatut(StatutEvenement statut);
    public List<Evenement> searchEvents(String keyword);

    public Evenement addEvent(EvenementDtoAdd dto, MultipartFile imageFile, MultipartFile document) throws StorageProblemException;
    public Evenement addEvent(EvenementDtoAddIn dto) throws IOException, StorageProblemException; // dto avec les fichiers aussi

    public Evenement stockageDuDocument(Evenement evenement, MultipartFile document) throws StorageProblemException;
    public Evenement stockageDeLimage(Evenement evenement, MultipartFile image) throws StorageProblemException;

    public Evenement editEvent(Long id, Evenement evenement, MultipartFile imageFile, MultipartFile document) throws StorageProblemException, EventNotFoundException;

    public void deleteEventById(Long id) throws EventNotFoundException;
}
