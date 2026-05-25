package com.ouadia.rovista1.services.implementations;

import com.ouadia.rovista1.Mapper.*;
import com.ouadia.rovista1.Mapper.UtilisateurMapper;
import com.ouadia.rovista1.Mapper.UtilisateurMapper;
import com.ouadia.rovista1.Mapper.UtilisateurMapper;
import com.ouadia.rovista1.dtos.UtilisateurDto;
import com.ouadia.rovista1.entities.*;
import com.ouadia.rovista1.entities.Utilisateur;
import com.ouadia.rovista1.entities.Utilisateur;
import com.ouadia.rovista1.entities.enums.StatutCompte;
import com.ouadia.rovista1.exceptions.UserNotFoundException;
import com.ouadia.rovista1.repositories.UtilisateurRepository;
import com.ouadia.rovista1.services.interfaces.IUtilisateurService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Service
@Transactional
@AllArgsConstructor
public class UtilisateurServiceImpl implements IUtilisateurService {

    private UtilisateurRepository repository;


    @Override
    public UtilisateurDto addUtilisateur(UtilisateurDto utilisateurDto) {
        Utilisateur utilisateur = UtilisateurMapper.mapToUtilisateur(utilisateurDto);
        if (repository.findById(utilisateur.getId()).isPresent()) {
            throw new RuntimeException(" utilisateur exsist ");
        } else
            return UtilisateurMapper.mapToUtilisateurDto(repository.save(utilisateur));
    }

    @Override
    public UtilisateurDto editUtilisateur(UtilisateurDto utilisateurDto, Long id) {
        Utilisateur utilisateur = UtilisateurMapper.mapToUtilisateur(utilisateurDto);
        if (utilisateur == null) return null;
        else {
            Utilisateur utilisateur1 = repository.findById(id).get();
            if (utilisateur1 == null) {
                return null;
            }
            utilisateur1.setUsername(utilisateur.getUsername());
            utilisateur1.setEmail(utilisateur.getEmail());
            utilisateur1.setMotDePasse(utilisateur.getMotDePasse());
            utilisateur1.setStatutCompte(utilisateur.getStatutCompte());
            utilisateur1.setPhone(utilisateur.getPhone());
            utilisateur1.setAdresse(utilisateur.getAdresse());
            utilisateur1.setNotifications(utilisateur.getNotifications());
            utilisateur1.setRoles(utilisateur.getRoles());
            return UtilisateurMapper.mapToUtilisateurDto(repository.save(utilisateur1));
        }
    }

    @Override
    public UtilisateurDto editUtilisateurMap(Long id, Map<String, Object> map) {
        if (map == null) return null;
        else {
            Utilisateur utilisateur1 = repository.findById(id).get();
            if (utilisateur1 == null) {
                return null;
            }
            if (map.containsKey("username")) {
                utilisateur1.setUsername((String) map.get("username"));
            }
            if (map.containsKey("email")) {
                utilisateur1.setEmail((String) map.get("email"));
            }
            if (map.containsKey("motDePasse")) {
                utilisateur1.setMotDePasse((String) map.get("motDePasse"));
            }
            if (map.containsKey("statutCompte")) {
                utilisateur1.setStatutCompte(StatutCompte.valueOf(map.get("statutCompte").toString()));
            }
            if (map.containsKey("phone")) {
                utilisateur1.setPhone((String) map.get("phone"));
            }
            if (map.containsKey("adresse")) {
                utilisateur1.setAdresse((String) map.get("adresse"));
            }
            if (map.containsKey("notifications")) {
                utilisateur1.setNotifications((List<Notification>) map.get("notifications"));
            }
            if (map.containsKey("roles")) {
                utilisateur1.setRoles((List<Role>) map.get("roles"));
            }
            return UtilisateurMapper.mapToUtilisateurDto(repository.save(utilisateur1));
        }
    }

    @Override
    public UtilisateurDto getUtilisateurById(Long id) throws UserNotFoundException {
        return UtilisateurMapper.mapToUtilisateurDto(repository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("utilisateur not found")));
    }

    @Override
    public List<UtilisateurDto> getAllUtilisateurs() {
        return (repository.findAll().stream().map(utilisateur -> UtilisateurMapper.mapToUtilisateurDto(utilisateur)).toList());

    }

    @Override
    public void deleteUtilisateurById(Long id) {
        repository.deleteById(id);
    }

    @Override
    public void deleteAllByIds(Long... ids) {
        for (Long id : ids) {
            deleteUtilisateurById(id);
        }
    }
}
