package com.ouadia.rovista1.services.implementations;


import com.ouadia.rovista1.dtos.reservation.ReservationRequestDto;
import com.ouadia.rovista1.dtos.reservation.ReservationResponseDto;
import com.ouadia.rovista1.entities.*;
import com.ouadia.rovista1.entities.Reservation;

import com.ouadia.rovista1.entities.enums.StatutReservation;


import com.ouadia.rovista1.exceptions.ReservationNotFoundException;
import com.ouadia.rovista1.repositories.ReservationRepository;
import com.ouadia.rovista1.services.interfaces.IReservationService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
@Transactional
@AllArgsConstructor
public class ReservationServiceImpl implements IReservationService {

    private ReservationRepository repository;
    private com.ouadia.rovista1.mappers.ReservationMapper mapper;


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
}
