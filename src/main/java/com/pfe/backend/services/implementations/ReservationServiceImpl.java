package com.pfe.backend.services.implementations;


import com.pfe.backend.dtos.reservation.HistoriqueReservationDto;
import com.pfe.backend.dtos.reservation.ReservationRequestDto;
import com.pfe.backend.dtos.reservation.ReservationResponseDto;
import com.pfe.backend.entities.*;
import com.pfe.backend.entities.*;

import com.pfe.backend.entities.enums.StatutPaiement;
import com.pfe.backend.entities.enums.StatutReservation;


import com.pfe.backend.exceptions.ReservationNotFoundException;
import com.pfe.backend.mappers.ReservationMapper;
import com.pfe.backend.repositories.ReservationRepository;
import com.pfe.backend.services.interfaces.IReservationService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
@Transactional
@AllArgsConstructor
public class ReservationServiceImpl implements IReservationService {

    private final ReservationRepository repository;
    private ReservationMapper mapper;


    @Override
    public ReservationResponseDto addReservation(ReservationRequestDto reservationDto) {
        Reservation reservation = mapper.mappingReservationDtoRequestToReservation(reservationDto);
            if (repository.existsByClient(reservation.getClient()) || repository.existsByVisiteurInvite(reservation.getVisiteurInvite())){
            return mapper.mappingReservationToReservationDtoResponse(repository.save(reservation));
            }
        throw new RuntimeException("Client or VisiteurInvite not found");
    }


    @Override
    public ReservationResponseDto editReservation(ReservationRequestDto reservationDto, Long id) {
        Reservation reservation= mapper.mappingReservationDtoRequestToReservation(reservationDto);
        if (reservation==null)return null;
        else {
            Reservation reservation1 =repository.findById(id).get();
            if (reservation1==null)return null;
            reservation1.setDateReservation(reservation.getDateReservation());
            reservation1.setNombrePlaces(reservation.getNombrePlaces());
            reservation1.setStatut(reservation.getStatut());
            reservation1.setMontant(reservation.getMontant());
            reservation1.setBillets(reservation.getBillets());
            reservation1.setPaiement(reservation.getPaiement());
            reservation1.setEvenement(reservation.getEvenement());
            reservation1.setVisiteurInvite(reservation.getVisiteurInvite());
            reservation1.setClient(reservation.getClient());
            return mapper.mappingReservationToReservationDtoResponse(repository.save(reservation1));
        }
    }

    @Override
    public ReservationResponseDto editReservationMap(Long id, Map<String, Object> map) {
        if (map == null ) return null;
        else {
            Reservation reservation1 = repository.findById(id).get();
            if (reservation1 == null) {
                return null;
            }
            if (map.containsKey("dateReservation")) {
                reservation1.setDateReservation((LocalDateTime) map.get("dateReservation"));
            }
            if (map.containsKey("nombrePlaces")) {
                reservation1.setNombrePlaces((int) map.get("nombrePlaces"));
            }
            if (map.containsKey("statut")) {
                reservation1.setStatut(StatutReservation.valueOf(map.get("statut").toString()));
            }
            if (map.containsKey("montant")) {
                reservation1.setMontant((BigDecimal) map.get("montant"));
            }
            if (map.containsKey("billets")) {
                reservation1.setBillets((List<Billet>) map.get("billets"));
            }
            if (map.containsKey("paiement")) {
                reservation1.setPaiement((Paiement) map.get("paiement"));
            }
            if (map.containsKey("evenement")) {
                reservation1.setEvenement((Evenement) map.get("evenement"));
            }
            if (map.containsKey("visiteurInvite")) {
                reservation1.setVisiteurInvite((VisiteurInvite) map.get("visiteurInvite"));
            }
            if (map.containsKey("client")) {
                reservation1.setClient((Client) map.get("client"));
            }

            return mapper.mappingReservationToReservationDtoResponse(repository.save(reservation1));
        }
    }

    @Override
    public ReservationResponseDto getReservationById(Long id) throws ReservationNotFoundException {
        Reservation reservation = repository.findById(id).orElseThrow(() -> new ReservationNotFoundException("Reservation not found"));
        return mapper.mappingReservationToReservationDtoResponse(reservation);
    }

//    @Override
//    public Page<ReservationResponseDto> getReservationsByClientId(Long clientId, int page, int size) throws ClientNotFoundException {
//        Pageable pageable = PageRequest.of(page, size);
//
//        Page<Reservation> reserationPage = repository.findByClientId(clientId, pageable);
//
//        List<ReservationResponseDto> dtoList = new ArrayList<>();
//        for (Reservation reservation: reserationPage.getContent()){
//            ReservationResponseDto dto = reservationMapper.mapping(reservation);
//            dtoList.add(dto);
//        }
//
//        PageResponse<EvenementResponseDto> response = new PageResponse<>();
//
//        response.setContent(dtoList);
//        response.setPage(evenementPage.getNumber());
//        response.setSize(evenementPage.getSize());
//        response.setTotalElements(evenementPage.getTotalElements());
//        response.setTotalPages(evenementPage.getTotalPages());
//
//        return response;
//        return repository.findByClientId(clientId);
//    }

    @Override
    public Reservation getReservationEntityById(Long id)
            throws ReservationNotFoundException {

        return repository.findById(id)
                .orElseThrow(() ->
                        new ReservationNotFoundException("Reservation not found"));
    }

    @Override
    public List<ReservationResponseDto> getReservationByClient(Client client) throws ReservationNotFoundException {
        return (repository.findByClient(client).stream().map(reservation-> mapper.mappingReservationToReservationDtoResponse(reservation)).toList());
    }

    @Override
    public List<ReservationResponseDto> getReservationByVisiteur(VisiteurInvite visiteurInvite) throws ReservationNotFoundException {
        return (repository.findByVisiteurInvite(visiteurInvite).stream().map(reservation-> mapper.mappingReservationToReservationDtoResponse(reservation)).toList());
    }

    @Override
    public List<ReservationResponseDto> getAllReservations() {
        return (repository.findAll().stream().map(reservation->mapper.mappingReservationToReservationDtoResponse(reservation)).toList());
    }

    @Override
    public void deleteReservationById(Long id) {
        repository.deleteById(id);
    }

    @Override
    public void deleteAllByIds(Long... ids) {
        for (Long id :ids){
            deleteReservationById(id);
        }
    }

    //_____________________________________________

    @Override
    public Page<HistoriqueReservationDto> getBookings(
            Long clientId, String statut, int page, int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<Reservation> pageR;
        if (statut != null && !statut.isBlank()) {
            StatutReservation status = mapStatut(statut);
            pageR = repository
                    .findByClientIdAndStatutOrderByDateReservationDesc(clientId, status, pageable);
        } else {
            pageR = repository
                    .findByClientIdOrderByDateReservationDesc(clientId, pageable);
        }
        return pageR.map(this::toDTO);
    }

    // Génère un PDF via iText / JasperReports

    @Override
    public byte[] generateTicketPdf(Long reservationId, Long clientId) {
        Reservation r = repository.findByIdAndClientId(reservationId, clientId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Réservation introuvable"));

        if (r.getStatut() != StatutReservation.CONFIRME) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Billet non disponible pour ce statut");
        }

        // Exemple minimaliste — adaptez avec iText/Jasper/Flying Saucer
        return buildPdf(r);
    }

    @Override
    public HistoriqueReservationDto toDTO(Reservation r) {
        return HistoriqueReservationDto.builder()
                .id(r.getId())
                .titre(r.getEvenement().getTitre())
                .date(r.getEvenement().getDateDebut().toString())
                .lieu(r.getEvenement().getVille())
                .prix(r.getEvenement().getPrix())
                .statut(formatStatut(r.getStatut()))
                .paiement(formatPaiement(r.getPaiement().getStatut()))
                .build();
    }

    private String formatStatut(StatutReservation s) {
        return switch (s) {
            case CONFIRME   -> "Confirmé";
            case EN_ATTENTE -> "En attente";
            case ANNULEE     -> "Annulé";
        };
    }

    private String formatPaiement(StatutPaiement p) {
        return switch (p) {
            case VALIDE        -> "Payé";
            case EN_ATTENTE  -> "En attente";
            case ANNULE   -> "Annulé";
            case ECHOUE   -> "Échoue";
        };
    }

    @Override
    public StatutReservation mapStatut(String s) {
        return switch (s) {
            case "Confirmé"   -> StatutReservation.CONFIRME;
            case "En attente" -> StatutReservation.EN_ATTENTE;
            case "Annulé"     -> StatutReservation.ANNULEE;
            default -> throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Statut inconnu : " + s);
        };
    }


    @Override
    public byte[] buildPdf(Reservation r) {
        // TODO : intégrer iText 7 ou JasperReports
        // Retourne un byte[] du PDF généré
        throw new UnsupportedOperationException("Implémenter la génération PDF");
    }
}
