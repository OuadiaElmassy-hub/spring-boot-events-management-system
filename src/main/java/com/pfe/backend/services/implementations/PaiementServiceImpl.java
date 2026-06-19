package com.pfe.backend.services.implementations;

import com.pfe.backend.dtos.paiement.PaiementRequestDto;
import com.pfe.backend.dtos.paiement.PaiementResponseDto;
import com.pfe.backend.entities.Paiement;
import com.pfe.backend.entities.Reservation;
import com.pfe.backend.entities.enums.MethodePaiement;
import com.pfe.backend.entities.enums.StatutPaiement;
import com.pfe.backend.exceptions.PaiementNotFoundException;
import com.pfe.backend.exceptions.ReservationNotFoundException;
import com.pfe.backend.mappers.PaiementMapper;
import com.pfe.backend.services.interfaces.IPaiementService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import com.pfe.backend.repositories.PaiementRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
@Transactional
@AllArgsConstructor
public class PaiementServiceImpl implements IPaiementService {

    private PaiementRepository repository;
    private PaiementMapper paiementMapper;


    @Override
    public PaiementResponseDto addPaiement(PaiementRequestDto paiementDto) throws ReservationNotFoundException {
        Paiement paiement= paiementMapper.mappingPaiementDtoRequestToPaiement(paiementDto);
            return paiementMapper.mappingPaiementToPaiementDtoResponse(repository.save(paiement));
    }

    @Override
    public PaiementResponseDto editPaiement(PaiementRequestDto paiementDto, Integer id) throws ReservationNotFoundException {

        Paiement paiement= paiementMapper.mappingPaiementDtoRequestToPaiement(paiementDto);
       if (paiement==null)return null;
       else {
           Paiement paiement1 =repository.findById(id).get();
           if (paiement1==null)return null;
           paiement1.setMontant(paiement.getMontant());
           paiement1.setDatePaiement(paiement.getDatePaiement());
           paiement1.setStatut(paiement.getStatut());
           paiement1.setMethodePaiement(paiement.getMethodePaiement());
           paiement1.setReservation(paiement.getReservation());
           return paiementMapper.mappingPaiementToPaiementDtoResponse(repository.save(paiement1));
       }
    }

    @Override
    public PaiementResponseDto editPaiementMap(Integer id, Map<String, Object> map) {
        if (map == null ) return null;
        else {
            Paiement paiement1 = repository.findById(id).get();
            if (paiement1 == null) {
                return null;
            }
            if (map.containsKey("montant")) {
                paiement1.setMontant((BigDecimal) map.get("montant"));
            }
            if (map.containsKey("datePaiement")) {
                paiement1.setDatePaiement((LocalDateTime) map.get("datePaiement"));
            }
            if (map.containsKey("statut")) {
                paiement1.setStatut(StatutPaiement.valueOf(map.get("statut").toString()));
            }
            if (map.containsKey("methodePaiement")) {
                paiement1.setMethodePaiement(MethodePaiement.valueOf(map.get("methodePaiement").toString()));
            }
            if (map.containsKey("reservation")) {
                paiement1.setReservation((Reservation) map.get("reservation"));
            }
            return paiementMapper.mappingPaiementToPaiementDtoResponse(repository.save(paiement1));
        }
    }

    @Override
    public PaiementResponseDto getPaiementById(Integer id) throws PaiementNotFoundException {
        Paiement paiement = repository.findById(id).orElseThrow(() -> new PaiementNotFoundException("paiement not found"));
        return paiementMapper.mappingPaiementToPaiementDtoResponse(paiement);
    }

    @Override
    public List<PaiementResponseDto> getAllPaiements() {
        return (repository.findAll().stream().map(paiement-> paiementMapper.mappingPaiementToPaiementDtoResponse(paiement)).toList());
    }

    @Override
    public void deletePaiementById(Integer id) {
        repository.deleteById(id);
    }

    @Override
    public void deleteAllByIds(Integer... ids) {
        for (Integer id :ids){
            deletePaiementById(id);
        }
    }
}
