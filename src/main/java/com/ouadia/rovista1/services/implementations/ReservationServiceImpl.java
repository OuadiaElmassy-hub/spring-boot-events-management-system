package com.ouadia.rovista1.services.implementations;


import com.ouadia.rovista1.Mapper.ReservationMapper;


import com.ouadia.rovista1.dtos.ReservationDto;
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


    @Override
    public ReservationDto addReservation(ReservationDto reservationDto) {
        Reservation reservation= ReservationMapper.mapToReservation(reservationDto);
        if (repository.existsById(reservation.getId())){
            throw new RuntimeException(" reservation not exsist ");
        }else
            return ReservationMapper.mapToReservationDto(repository.save(reservation));
    }

    @Override
    public ReservationDto editReservation(ReservationDto reservationDto, Long id) {
        Reservation reservation= ReservationMapper.mapToReservation(reservationDto);
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
            return ReservationMapper.mapToReservationDto(repository.save(reservation1));
        }
    }

    @Override
    public ReservationDto editReservationMap(Long id, Map<String, Object> map) {
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

            return ReservationMapper.mapToReservationDto(repository.save(reservation1));
        }
    }

    @Override
    public ReservationDto getReservationById(Long id) throws ReservationNotFoundException {
        Reservation reservation = repository.findById(id).orElseThrow(() -> new ReservationNotFoundException("Reservation not found"));
        return ReservationMapper.mapToReservationDto(reservation);
    }

    @Override
    public List<ReservationDto> getReservationByClient(Client client) throws ReservationNotFoundException {
        return (repository.findByClient(client).stream().map(reservation-> ReservationMapper.mapToReservationDto(reservation)).toList());
    }

    @Override
    public List<ReservationDto> getAllReservations() {
        return (repository.findAll().stream().map(reservation-> ReservationMapper.mapToReservationDto(reservation)).toList());
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
