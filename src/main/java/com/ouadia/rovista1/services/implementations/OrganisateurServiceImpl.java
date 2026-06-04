package com.ouadia.rovista1.services.implementations;

import com.ouadia.rovista1.entities.Organisateur;
import com.ouadia.rovista1.entities.Notification;
import com.ouadia.rovista1.entities.Role;
import com.ouadia.rovista1.entities.enums.StatutCompte;
import com.ouadia.rovista1.repositories.OrganisateurRepository;
import com.ouadia.rovista1.services.interfaces.IOrganisateurService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@Transactional
@AllArgsConstructor
public class OrganisateurServiceImpl implements IOrganisateurService {

    private OrganisateurRepository repository;

    @Override
    public OrganisateurDto addOrganisateur(OrganisateurDto organisateurDto) {
        Organisateur organisateur= OrganisateurMapper.mapToOrganisateur(organisateurDto);
            return OrganisateurMapper.mapToOrganisateurDto(repository.save(organisateur));
    }

    @Override
    public OrganisateurDto editOrganisateur(OrganisateurDto organisateurDto ,Long idReche) {
        Organisateur organisateur= OrganisateurMapper.mapToOrganisateur(organisateurDto);
        if (organisateur == null) return null;
        else {
            Organisateur organisateur1 = repository.findById(idReche).get();
            if (organisateur1 == null) {return null;}
            organisateur1.setUsername(organisateur.getUsername());
            organisateur1.setEmail(organisateur.getEmail());
            organisateur1.setMotDePasse(organisateur.getMotDePasse());
            organisateur1.setStatutCompte(organisateur.getStatutCompte());
            organisateur1.setPhone(organisateur.getPhone());
            organisateur1.setAdresse(organisateur.getAdresse());
            organisateur1.setNotifications(organisateur.getNotifications());
            organisateur1.setRoles(organisateur.getRoles());
            return OrganisateurMapper.mapToOrganisateurDto(repository.save(organisateur1));
        }
    }

    @Override
    public OrganisateurDto editOrganisateurMap(Long idReche, Map<String, Object> map) {
        if (map == null ) return null;
        else {
            Organisateur organisateur1 = repository.findById(idReche).get();
            if (organisateur1 == null) {return null;}
            if (map.containsKey("username")){
                organisateur1.setUsername((String) map.get("username"));
            }
            if (map.containsKey("email")){
                organisateur1.setEmail((String) map.get("email"));
            }
            if (map.containsKey("motDePasse")){
                organisateur1.setMotDePasse((String) map.get("motDePasse"));
            }
            if (map.containsKey("statutCompte")){
                organisateur1.setStatutCompte(StatutCompte.valueOf(map.get("statutCompte").toString()));
            }
            if (map.containsKey("phone")) {
                organisateur1.setPhone((String) map.get("phone"));
            }
            if (map.containsKey("adresse")) {
                organisateur1.setAdresse((String) map.get("adresse"));
            }
            if (map.containsKey("notifications")) {
                organisateur1.setNotifications((List<Notification>)map.get("notifications"));
            }
            if (map.containsKey("roles")) {
                organisateur1.setRoles((List<Role>)map.get("roles"));
            }
            return OrganisateurMapper.mapToOrganisateurDto(repository.save(organisateur1));
        }
    }


    @Override
    public OrganisateurDto getOrganisateurById(Long id) {
        return OrganisateurMapper.mapToOrganisateurDto(repository.findById(id)
                .orElseThrow(()->new RuntimeException("organisateur not found")));
    }

    @Override
    public List<OrganisateurDto> getAllOrganisateurs() {
        return (repository.findAll().stream().map(organisateur->OrganisateurMapper.mapToOrganisateurDto(organisateur)).toList());
    }

    @Override
    public void deleteOrganisateurById(Long id) {
        repository.deleteById(id);
    }

    @Override
    public void deleteAllByIds(Long... ids) {
        for (Long id :ids){
            deleteOrganisateurById(id);
        }
    }
}
