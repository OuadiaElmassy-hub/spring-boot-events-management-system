package com.ouadia.rovista1.services.implementations;

import com.ouadia.rovista1.dtos.avis.AvisRequestDto;
import com.ouadia.rovista1.dtos.avis.AvisResponseDto;
import com.ouadia.rovista1.entities.*;
import com.ouadia.rovista1.exceptions.AvisNotFoundException;
import com.ouadia.rovista1.mappers.AvisMapper;
import com.ouadia.rovista1.repositories.AvisRepository;
import com.ouadia.rovista1.services.interfaces.IAvisService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
public class AvisServiceImpl implements IAvisService {

    private AvisRepository repository;



    @Override
    public AvisResponseDto addAvisDto(AvisRequestDto avisDto) {
        Avis avis = AvisMapper.mappingAvisDtoRequestToAvis(avisDto);
            return  AvisMapper.mappingAvisToAvisDtoResponse(repository.save(avis));
    }

    @Override
    public AvisResponseDto editAvis(AvisRequestDto avisDto, Long idRrch) {
        Avis avis = AvisMapper.mappingAvisDtoRequestToAvis(avisDto);
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
            return AvisMapper.mappingAvisToAvisDtoResponse(repository.save(avis1));
        }
    }

    @Override
    public AvisResponseDto editAvisMap(Long idRrch, Map<String, Object> map) {
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
            return AvisMapper.mappingAvisToAvisDtoResponse(repository.save(avis1));
        }
    }

    @Override
    public AvisResponseDto getAvisById(Long id)throws AvisNotFoundException {
        Avis avis=repository.findById(id).orElseThrow(()->new AvisNotFoundException("Avis not found"));
        return AvisMapper.mappingAvisToAvisDtoResponse(avis);

    }


    @Override
    public List<AvisResponseDto> getAvisByEvenementId(Long evenementId) {
        return repository.findByEvenementId(evenementId)
                .stream()
                .map(AvisMapper::mappingAvisToAvisDtoResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<AvisResponseDto> getAllAvis() {
        return (repository.findAll().stream().map(avis-> AvisMapper.mappingAvisToAvisDtoResponse(avis)).toList());

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
