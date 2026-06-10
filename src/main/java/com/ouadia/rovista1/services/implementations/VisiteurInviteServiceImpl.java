package com.ouadia.rovista1.services.implementations;


import com.ouadia.rovista1.dtos.visiteur.VisiteurInviteRequestDto;
import com.ouadia.rovista1.dtos.visiteur.VisiteurInviteResponseDto;
import com.ouadia.rovista1.entities.*;

import com.ouadia.rovista1.entities.VisiteurInvite;

import com.ouadia.rovista1.exceptions.UserNotFoundException;

import com.ouadia.rovista1.mappers.VisiteurMapper;
import com.ouadia.rovista1.repositories.VisiteurRepository;
import com.ouadia.rovista1.services.interfaces.IVisiteurService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@Transactional
@AllArgsConstructor
public class VisiteurInviteServiceImpl implements IVisiteurService {

    private VisiteurRepository repository;
    private final VisiteurMapper visiteurMapper;
    @Override
    public VisiteurInviteResponseDto addVisiteur(VisiteurInviteRequestDto visiteurInviteDto) {
        VisiteurInvite visiteur = visiteurMapper.mappingVisiteurInviteDtoRequestToVisiteurInvite(visiteurInviteDto);
            return visiteurMapper.mappingVisiteurInviteToVisiteurInviteDtoResponse(repository.save(visiteur));
    }

    @Override
    public VisiteurInviteResponseDto editVisiteur(VisiteurInviteRequestDto visiteurInviteDto, Long id) {
        VisiteurInvite visiteur = visiteurMapper.mappingVisiteurInviteDtoRequestToVisiteurInvite(visiteurInviteDto);
        if (visiteur == null) return null;
        else {
            VisiteurInvite visiteur1 = repository.findById(id).get();
            if (visiteur1 == null) {
                return null;
            }
            visiteur1.setNom(visiteur.getNom());
            visiteur1.setPrenom(visiteur.getPrenom());
            visiteur1.setEmail(visiteur.getEmail());
            visiteur1.setPhone(visiteur.getPhone());
            visiteur1.setAdresse(visiteur.getAdresse());
            visiteur1.setReservations(visiteur.getReservations());
            visiteur1.setAvis(visiteur.getAvis());
            return visiteurMapper.mappingVisiteurInviteToVisiteurInviteDtoResponse(repository.save(visiteur1));
        }
    }

    @Override
    public VisiteurInviteResponseDto editVisiteurMap(Long id, Map<String, Object> map) {
        if (map == null) return null;
        else {
            VisiteurInvite visiteur1 = repository.findById(id).get();
            if (visiteur1 == null) {
                return null;
            }
            if (map.containsKey("nom")) {
                visiteur1.setNom((String) map.get("nom"));
            }
            if (map.containsKey("prenom")) {
                visiteur1.setPrenom((String) map.get("prenom"));
            }
            if (map.containsKey("email")) {
                visiteur1.setEmail((String) map.get("email"));
            }
            if (map.containsKey("phone")) {
                visiteur1.setPhone((String) map.get("phone"));
            }
            if (map.containsKey("adresse")) {
                visiteur1.setAdresse((String) map.get("adresse"));
            }
            if (map.containsKey("reservations")) {
                visiteur1.setReservations((List<Reservation>) map.get("reservations"));
            }
            if (map.containsKey("avis")) {
                visiteur1.setAvis((List<Avis>) map.get("avis"));
            }
            return visiteurMapper.mappingVisiteurInviteToVisiteurInviteDtoResponse(repository.save(visiteur1));
        }
    }

    @Override
    public VisiteurInviteResponseDto getVisiteurById(Long id) throws UserNotFoundException {
        return visiteurMapper.mappingVisiteurInviteToVisiteurInviteDtoResponse(repository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("Visiteur not found")));
    }

    @Override
    public List<VisiteurInviteResponseDto> getAllVisiteurs() {
        return (repository.findAll().stream().
                map(visiteurMapper::mappingVisiteurInviteToVisiteurInviteDtoResponse)
                .toList());

    }

    @Override
    public void deleteVisiteurById(Long id) {
        repository.deleteById(id);
    }

    @Override
    public void deleteAllByIds(Long... ids) {
        for (Long id : ids) {
            deleteVisiteurById(id);
        }
    }
}
