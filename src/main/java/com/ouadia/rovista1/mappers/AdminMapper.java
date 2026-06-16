package com.ouadia.rovista1.mappers;

import com.ouadia.rovista1.dtos.AdminDto;
import com.ouadia.rovista1.entities.Admin;

public class AdminMapper {
    public static AdminDto mapToAdminDto(Admin admin){
        return new AdminDto(
                admin.getId(),
                admin.getUsername(),
                admin.getEmail(),
                admin.getMotDePasse(),
                admin.getStatutCompte(),
                admin.getPhone(),
                admin.getAdresse(),
                admin.getNotifications(),
                admin.getRoles(),
                admin.getNom(),
                admin.getPrenom(),
                admin.getDateNaissance()
        );
    }
    public static Admin mapToAdmin(AdminDto adminDto){
        return new Admin(
                adminDto.getId(),
                adminDto.getUsername(),
                adminDto.getEmail(),
                adminDto.getMotDePasse(),
                adminDto.getStatutCompte(),
                adminDto.getPhone(),
                adminDto.getAdresse(),
                adminDto.getNotifications(),
                adminDto.getRoles(),
                adminDto.getNom(),
                adminDto.getPrenom(),
                adminDto.getDateNaissance()
        );
    }
}
