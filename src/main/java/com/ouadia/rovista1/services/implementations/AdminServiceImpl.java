package com.ouadia.rovista1.services.implementations;

import com.ouadia.rovista1.mappers.AdminMapper;
import com.ouadia.rovista1.dtos.AdminDto;
import com.ouadia.rovista1.entities.Admin;
import com.ouadia.rovista1.entities.Notification;
import com.ouadia.rovista1.entities.Role;
import com.ouadia.rovista1.entities.enums.StatutCompte;
import com.ouadia.rovista1.repositories.AdminRepository;
import com.ouadia.rovista1.services.interfaces.IAdminService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Service
@Transactional
@AllArgsConstructor
public class AdminServiceImpl implements IAdminService {

    private AdminRepository repository;

    @Override
    public AdminDto addAdmin(AdminDto adminDto) {
        Admin admin= AdminMapper.mapToAdmin(adminDto);
        return AdminMapper.mapToAdminDto(repository.save(admin));
    }

    @Override
    public AdminDto editAdmin(AdminDto adminDto ,Long idReche) {
        Admin admin= AdminMapper.mapToAdmin(adminDto);
        if (admin == null) return null;
        else {
            Admin admin1 = repository.findById(idReche).get();
            if (admin1 == null) {return null;}
                admin1.setUsername(admin.getUsername());
                admin1.setEmail(admin.getEmail());
                admin1.setMotDePasse(admin.getMotDePasse());
                admin1.setStatutCompte(admin.getStatutCompte());
                admin1.setPhone(admin.getPhone());
                admin1.setAdresse(admin.getAdresse());
                admin1.setNotifications(admin.getNotifications());
                admin1.setRoles(admin.getRoles());
                admin1.setNom(admin.getNom());
                admin1.setPrenom(admin.getPrenom());
                admin1.setDateNaissance(admin.getDateNaissance());
                return AdminMapper.mapToAdminDto(repository.save(admin1));
            }
        }

    @Override
    public AdminDto editAdminMap(Long idReche, Map<String, Object> map) {
        if (map == null ) return null;
        else {
            Admin admin1 = repository.findById(idReche).get();
            if (admin1 == null) {return null;}
            if (map.containsKey("username")){
            admin1.setUsername((String) map.get("username"));
            }
            if (map.containsKey("email")){
            admin1.setEmail((String) map.get("email"));
            }
            if (map.containsKey("motDePasse")){
            admin1.setMotDePasse((String) map.get("motDePasse"));
            }
            if (map.containsKey("statutCompte")){
            admin1.setStatutCompte(StatutCompte.valueOf(map.get("statutCompte").toString()));
            }
            if (map.containsKey("phone")) {
                admin1.setPhone((String) map.get("phone"));
            }
            if (map.containsKey("adresse")) {
                admin1.setAdresse((String) map.get("adresse"));
            }
            if (map.containsKey("notifications")) {
                admin1.setNotifications((List<Notification>)map.get("notifications"));
            }
            if (map.containsKey("roles")) {
            admin1.setRoles((List<Role>)map.get("roles"));
            }
            if (map.containsKey("nom")) {
                admin1.setNom((String) map.get("nom"));
            }
            if (map.containsKey("prenom")) {
                admin1.setPrenom((String) map.get("prenom"));
            }
            if (map.containsKey("dateNaissance")) {
                admin1.setDateNaissance((LocalDate) map.get("dateNaissance"));
            }
            return AdminMapper.mapToAdminDto(repository.save(admin1));
        }
    }


    @Override
    public AdminDto getAdminById(Long id) {
        return AdminMapper.mapToAdminDto(repository.findById(id)
                        .orElseThrow(()->new RuntimeException("admin not found")));
    }

    @Override
    public List<AdminDto> getAllAdmins() {
        return (repository.findAll().stream().map(admin->AdminMapper.mapToAdminDto(admin)).toList());
    }

    @Override
    public void deleteAdminById(Long id) {
        repository.deleteById(id);
    }

    @Override
    public void deleteAllByIds(Long... ids) {
         for (Long id :ids){
             deleteAdminById(id);
         }
    }
}
