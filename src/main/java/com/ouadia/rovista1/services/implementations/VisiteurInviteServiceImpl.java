package com.ouadia.rovista1.services.implementations;


import com.ouadia.rovista1.Mapper.VisiteurMapper;
import com.ouadia.rovista1.dtos.VisiteurInviteDto;
import com.ouadia.rovista1.entities.*;

import com.ouadia.rovista1.entities.VisiteurInvite;

import com.ouadia.rovista1.exceptions.UserNotFoundException;
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


    @Override
    public VisiteurInviteDto addVisiteur(VisiteurInviteDto visiteurInviteDto) {
        VisiteurInvite visiteur = VisiteurMapper.mapToVisiteurInvite(visiteurInviteDto);
        if (repository.findById(visiteur.getId()).isPresent()) {
            throw new RuntimeException(" visiteur exsist ");
        } else
            return VisiteurMapper.mapToVisiteurInviteDto(repository.save(visiteur));
    }

    @Override
    public VisiteurInviteDto editVisiteur(VisiteurInviteDto visiteurInviteDto, Long id) {
        VisiteurInvite visiteur = VisiteurMapper.mapToVisiteurInvite(visiteurInviteDto);
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
            return VisiteurMapper.mapToVisiteurInviteDto(repository.save(visiteur1));
        }
    }

    @Override
    public VisiteurInviteDto editVisiteurMap(Long id, Map<String, Object> map) {
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
            return VisiteurMapper.mapToVisiteurInviteDto(repository.save(visiteur1));
        }
    }

    @Override
    public VisiteurInviteDto getVisiteurById(Long id) throws UserNotFoundException {
        return VisiteurMapper.mapToVisiteurInviteDto(repository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("Visiteur not found")));
    }

    @Override
    public List<VisiteurInviteDto> getAllVisiteurs() {
        return (repository.findAll().stream().
                map(visiteur -> VisiteurMapper.mapToVisiteurInviteDto(visiteur))
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
