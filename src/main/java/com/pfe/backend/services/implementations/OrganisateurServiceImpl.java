package com.pfe.backend.services.implementations;

import com.pfe.backend.exceptions.OrganisateurNotFoundException;
import com.pfe.backend.mappers.OrganisateurMapper;
import com.pfe.backend.dtos.organisateur.OrganisateurRequestDto;
import com.pfe.backend.dtos.organisateur.OrganisateurResponseDto;
import com.pfe.backend.entities.Organisateur;
import com.pfe.backend.entities.Notification;
import com.pfe.backend.entities.Role;
import com.pfe.backend.entities.enums.StatutCompte;
import com.pfe.backend.exceptions.BusinessException;
import com.pfe.backend.repositories.OrganisateurRepository;
import com.pfe.backend.services.interfaces.IOrganisateurService;
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
    private OrganisateurMapper organisateurMapper;

    @Override
    public OrganisateurResponseDto addOrganisateur(OrganisateurRequestDto organisateurRequestDto) throws BusinessException {

        Organisateur org = organisateurMapper.mapToOrganisateur(organisateurRequestDto);
        if (repository.findByNumRegistre(organisateurRequestDto.getNumRegistre()).isPresent()){
            throw new BusinessException(" organisateur exsist ");
        }
        return organisateurMapper.mapToOrganisateurDto(repository.save(org));
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

        return organisateurMapper.mapToOrganisateurDto(repository.save(organisateur));
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
            return organisateurMapper.mapToOrganisateurDto(repository.save(organisateur1));
        }
    }


    @Override
    public OrganisateurResponseDto getOrganisateurById(Long id) throws OrganisateurNotFoundException {
        return organisateurMapper.mapToOrganisateurDto(repository.findById(id)
                .orElseThrow(()->new OrganisateurNotFoundException("organisateur not found with id : " + id)));
    }

    @Override
    public Organisateur getOrganisateurEntityById(Long id) {
        return repository.findById(id)
                .orElseThrow(()->new RuntimeException("organisateur not found"));
    }

    @Override
    public List<OrganisateurResponseDto> getAllOrganisateurs() {
        return (repository.findAll().stream().map(organisateur-> organisateurMapper.mapToOrganisateurDto(organisateur)).toList());
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
