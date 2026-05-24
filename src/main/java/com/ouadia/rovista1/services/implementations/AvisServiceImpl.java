package com.ouadia.rovista1.services.implementations;

import com.ouadia.rovista1.Mapper.AvisMapper;
import com.ouadia.rovista1.dtos.AvisDto;
import com.ouadia.rovista1.entities.*;
import com.ouadia.rovista1.exceptions.AvisNotFoundException;
import com.ouadia.rovista1.repositories.AvisRepository;
import com.ouadia.rovista1.services.interfaces.IAvisService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Transactional
@AllArgsConstructor
public class AvisServiceImpl implements IAvisService {

    private AvisRepository repository;


    @Override
    public AvisDto addAvisDto(AvisDto avisDto) {
        Avis avis = AvisMapper.mapToAvis(avisDto);
        if (repository.findById(avis.getId()).isPresent()){
            throw new RuntimeException(" avis exsist ");
        }else
            return  AvisMapper.mapToAvisDto(repository.save(avis));
    }

    @Override
    public AvisDto editAvis(AvisDto avisDto, Long idRrch) {
        Avis avis = AvisMapper.mapToAvis(avisDto);
        if (avis == null) return null;
        else {
            Avis avis1 = repository.findById(idRrch).get();
            if (avis1 == null) {return null;}
            avis1.setComment( avis.getComment());
            avis1.setNote( avis.getNote());
            avis1.setDateAvis( avis.getDateAvis());
            avis1.setEvenement( avis.getEvenement());
            avis1.setClient( avis.getClient());
            avis1.setVisiteur(avis.getVisiteur());
            return AvisMapper.mapToAvisDto(repository.save(avis1));
        }
    }

    @Override
    public AvisDto editAvisMap(Long idRrch, Map<String, Object> map) {
        if (map == null ) return null;
        else {
            Avis avis1 = repository.findById(idRrch).get();
            if (avis1 == null) {return null;}
            if (map.containsKey("comment")){
                avis1.setComment((String) map.get("comment"));
            }
            if (map.containsKey("note")){
                avis1.setNote((Double) map.get("note"));
            }
            if (map.containsKey("dateAvis")){
                avis1.setDateAvis((LocalDate) map.get("dateAvis"));
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
            return AvisMapper.mapToAvisDto(repository.save(avis1));
        }
    }

    @Override
    public AvisDto getAvisById(Long id)throws AvisNotFoundException{
        Avis avis=repository.findById(id).orElseThrow(()->new AvisNotFoundException("Avis not found"));
        return AvisMapper.mapToAvisDto(avis);

    }


    @Override
    public List<AvisDto> getAllAvis() {
        return (repository.findAll().stream().map(avis-> AvisMapper.mapToAvisDto(avis)).toList());

    }


    @Override
    public void deleteAvisById(Long id) {
        repository.deleteById(id);
    }

    @Override
    public void deleteAllByIds(Long... ids) {
        for (Long id :ids){
            deleteAvisById(id);
        }
    
    }
}
