package com.ouadia.rovista1.services.implementations;

import com.ouadia.rovista1.dtos.PageResponse;
import com.ouadia.rovista1.dtos.avis.AvisRequestDto;
import com.ouadia.rovista1.dtos.avis.AvisResponseDto;
import com.ouadia.rovista1.dtos.evenement.EvenementResponseDto;
import com.ouadia.rovista1.entities.*;
import com.ouadia.rovista1.exceptions.AvisNotFoundException;
import com.ouadia.rovista1.exceptions.EventNotFoundException;
import com.ouadia.rovista1.mappers.AvisMapper;
import com.ouadia.rovista1.repositories.AvisRepository;
import com.ouadia.rovista1.repositories.ClientRepository;
import com.ouadia.rovista1.repositories.EventRepository;
import com.ouadia.rovista1.repositories.ReservationRepository;
import com.ouadia.rovista1.services.EvenementSpecification;
import com.ouadia.rovista1.services.interfaces.IAvisService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import com.ouadia.rovista1.entities.enums.StatutReservation;
import org.springframework.web.server.ResponseStatusException;

@Service
@Transactional
@AllArgsConstructor
public class AvisServiceImpl implements IAvisService {


    private final AvisRepository avisRepository;
    private final AvisMapper avisMapper;
    private final EventRepository eventRepository;
    private final ReservationRepository reservationRepository;
    private final ClientRepository clientRepository;
    private final EventRepository evenementRepository;



    @Override
    public AvisResponseDto addAvis(AvisRequestDto avisDto) {
        Avis avis = avisMapper.mappingAvisDtoRequestToAvis(avisDto);
            return  avisMapper.mappingAvisToAvisDtoResponse(avisRepository.save(avis));
    }

    @Override
    public AvisResponseDto editAvis(AvisRequestDto avisDto, Long idRrch) {
        Avis avis = avisMapper.mappingAvisDtoRequestToAvis(avisDto);
        if (avis == null) return null;
        else {
            Avis avis1 = avisRepository.findById(idRrch).get();
            if (avis1 == null) {return null;}
            avis1.setComment( avis.getComment());
            avis1.setNote( avis.getNote());
            avis1.setDateAvis( avis.getDateAvis());
            avis1.setEvenement( avis.getEvenement());
            avis1.setClient( avis.getClient());
            avis1.setVisiteur(avis.getVisiteur());
            return avisMapper.mappingAvisToAvisDtoResponse(avisRepository.save(avis1));
        }
    }

    @Override
    public AvisResponseDto editAvisMap(Long idRrch, Map<String, Object> map) {
        if (map == null ) return null;
        else {
            Avis avis1 = avisRepository.findById(idRrch).get();
            if (avis1 == null) {return null;}
            if (map.containsKey("comment")){
                avis1.setComment((String) map.get("comment"));
            }
            if (map.containsKey("note")){
                avis1.setNote((Double) map.get("note"));
            }
            if (map.containsKey("dateAvis")){
                avis1.setDateAvis((LocalDateTime) map.get("dateAvis"));
            }

            if (map.containsKey("evenement")) {
                avis1.setEvenement((Evenement) map.get("evenement"));
            }
            if (map.containsKey("client")) {
                avis1.setClient((Client) map.get("client"));
            }
            if (map.containsKey("visiteur")) {
                avis1.setVisiteur((VisiteurInvite) map.get("visiteur"));
            }
            return avisMapper.mappingAvisToAvisDtoResponse(avisRepository.save(avis1));
        }
    }

    @Override
    public AvisResponseDto getAvisById(Long id)throws AvisNotFoundException {
        Avis avis=avisRepository.findById(id).orElseThrow(()->new AvisNotFoundException("Avis not found"));
        return avisMapper.mappingAvisToAvisDtoResponse(avis);

    }

    @Override
    public PageResponse<AvisResponseDto> getListAvisByEvenementId(int page, int size, Long id) throws EventNotFoundException {

        Pageable pageable = PageRequest.of(page, size);

        if (!eventRepository.existsById(id)){
            throw new EventNotFoundException("Event not found with id : " + id);
        }
        Page<AvisResponseDto> avisPage = avisRepository.findAvisByEvenementId(id, pageable);

//        List<AvisResponseDto> dtoList = new ArrayList<>();
//        for (Avis avis : avisPage.getContent()) {
//            AvisResponseDto dto = avisMapper.mappingAvisToAvisDtoResponse(avis);
//            dtoList.add(dto);
//        }

        PageResponse<AvisResponseDto> response = new PageResponse<>();

        response.setContent(avisPage.getContent());
        response.setPage(avisPage.getNumber());
        response.setSize(avisPage.getSize());
        response.setTotalElements(avisPage.getTotalElements());
        response.setTotalPages(avisPage.getTotalPages());

        return response;
    }

    @Override
    public List<AvisResponseDto> getAllAvis() {
        return (avisRepository.findAll().stream().map(avis-> avisMapper.mappingAvisToAvisDtoResponse(avis)).toList());

    }


    @Override
    public void deleteAvisById(Long id) {
        avisRepository.deleteById(id);
    }

    @Override
    public void deleteAllByIds(Long... ids) {
        for (Long id :ids){
            deleteAvisById(id);
        }
    
    }
    @Override
    public AvisResponseDto addAvisClient(Long clientId, Long evenementId, double note, String comment) {

        // Vérifie réservation confirmée
        boolean aReserve = reservationRepository.existsByClientIdAndEvenementIdAndStatut(
                clientId, evenementId, StatutReservation.CONFIRME);
        if (!aReserve)
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Vous devez avoir une réservation confirmée pour laisser un avis");
        // Vérifie pas déjà d'avis
        boolean dejaAvis = avisRepository.existsByClientIdAndEvenementId(clientId, evenementId);
        if (dejaAvis)
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Vous avez déjà laissé un avis pour cet événement");

        // Récupère client et événement
        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new RuntimeException("Client introuvable"));
        Evenement evenement = evenementRepository.findById(evenementId)
                .orElseThrow(() -> new RuntimeException("Événement introuvable"));

        // Crée l'avis
        Avis avis = Avis.builder()
                .note(note)
                .comment(comment)
                .dateAvis(LocalDateTime.now())
                .client(client)
                .evenement(evenement)
                .build();

        avis = avisRepository.save(avis);

        return avisMapper.mappingAvisToAvisDtoResponse(avis);
    }
}
