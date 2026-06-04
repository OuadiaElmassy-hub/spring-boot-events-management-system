package com.ouadia.rovista1.services.implementations;

import com.ouadia.rovista1.Mapper.OrganisateurMapper;
import com.ouadia.rovista1.dtos.organisateur.OrganisateurRequestDto;
import com.ouadia.rovista1.dtos.organisateur.OrganisateurResponseDto;
import com.ouadia.rovista1.entities.Organisateur;
import com.ouadia.rovista1.entities.Notification;
import com.ouadia.rovista1.entities.Role;
import com.ouadia.rovista1.entities.enums.StatutCompte;
import com.ouadia.rovista1.exceptions.BusinessException;
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
    public OrganisateurResponseDto addOrganisateur(OrganisateurRequestDto organisateurRequestDto) throws BusinessException {

        Organisateur org = OrganisateurMapper.mapToOrganisateur(organisateurRequestDto);
        if (repository.findByNumRegistre(organisateurRequestDto.getNumRegistre()).isPresent()){
            throw new BusinessException(" organisateur exsist ");
        }
        return OrganisateurMapper.mapToOrganisateurDto(repository.save(org));
    }

    @Override
    public OrganisateurResponseDto editOrganisateur(OrganisateurRequestDto organisateurRequestDto, Long idReche) throws BusinessException {

        Organisateur organisateur = repository.findById(idReche).orElseThrow
                (() -> new BusinessException(" organisateur exsist "));


        organisateur.setUsername(organisateur.getUsername());
        organisateur.setEmail(organisateur.getEmail());
        organisateur.setMotDePasse(organisateur.getMotDePasse());
        organisateur.setStatutCompte(organisateur.getStatutCompte());
        organisateur.setPhone(organisateur.getPhone());
        organisateur.setAdresse(organisateur.getAdresse());
        organisateur.setNotifications(organisateur.getNotifications());
        organisateur.setRoles(organisateur.getRoles());

        return OrganisateurMapper.mapToOrganisateurDto(repository.save(organisateur));
    }

    @Override
    public OrganisateurResponseDto editOrganisateurMap(Long idReche, Map<String, Object> map) throws BusinessException {

        if (map == null ) return null;
        else {
            Organisateur organisateur1 = repository.findById(idReche).orElseThrow
                    (() -> new BusinessException(" organisateur exsist "));
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
    public OrganisateurResponseDto getOrganisateurById(Long id) {
        return OrganisateurMapper.mapToOrganisateurDto(repository.findById(id)
                .orElseThrow(()->new RuntimeException("organisateur not found")));
    }

    @Override
    public List<OrganisateurResponseDto> getAllOrganisateurs() {
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
