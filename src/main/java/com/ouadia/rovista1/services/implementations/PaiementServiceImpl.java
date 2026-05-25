package com.ouadia.rovista1.services.implementations;

import com.ouadia.rovista1.Mapper.PaiementMapper;
import com.ouadia.rovista1.dtos.PaiementDto;
import com.ouadia.rovista1.entities.*;
import com.ouadia.rovista1.entities.Paiement;
import com.ouadia.rovista1.entities.enums.MethodePaiement;
import com.ouadia.rovista1.entities.enums.StatutPaiement;
import com.ouadia.rovista1.exceptions.PaiementNotFoundException;
import com.ouadia.rovista1.services.interfaces.IPaiementService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import com.ouadia.rovista1.repositories.PaiementRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
@Transactional
@AllArgsConstructor
public class PaiementServiceImpl implements IPaiementService {

    private PaiementRepository repository;


    @Override
    public PaiementDto addPaiement(PaiementDto paiementDto) {
        Paiement paiement= PaiementMapper.mapToPaiement(paiementDto);
        if (repository.existsById(paiement.getId())){
            throw new RuntimeException(" Error ");
        }else
            return PaiementMapper.mapToPaiementDto(repository.save(paiement));
    }

    @Override
    public PaiementDto editPaiement(PaiementDto paiementDto, Integer id) {

        Paiement paiement= PaiementMapper.mapToPaiement(paiementDto);
       if (paiement==null)return null;
       else {
           Paiement paiement1 =repository.findById(id).get();
           if (paiement1==null)return null;
           paiement1.setMontant(paiement.getMontant());
           paiement1.setDatePaiement(paiement.getDatePaiement());
           paiement1.setStatut(paiement.getStatut());
           paiement1.setMethodePaiement(paiement.getMethodePaiement());
           paiement1.setReservation(paiement.getReservation());
           return PaiementMapper.mapToPaiementDto(repository.save(paiement1));
       }
    }

    @Override
    public PaiementDto editPaiementMap(Integer id, Map<String, Object> map) {
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
            return PaiementMapper.mapToPaiementDto(repository.save(paiement1));
        }
    }

    @Override
    public PaiementDto getPaiementById(Integer id) throws PaiementNotFoundException {
        Paiement paiement = repository.findById(id).orElseThrow(() -> new PaiementNotFoundException("paiement not found"));
        return PaiementMapper.mapToPaiementDto(paiement);
    }

    @Override
    public List<PaiementDto> getAllPaiements() {
        return (repository.findAll().stream().map(paiement-> PaiementMapper.mapToPaiementDto(paiement)).toList());
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
