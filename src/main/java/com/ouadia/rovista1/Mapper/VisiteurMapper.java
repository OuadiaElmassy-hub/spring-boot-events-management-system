package com.ouadia.rovista1.Mapper;

import com.ouadia.rovista1.dtos.VisiteurInviteDto;
import com.ouadia.rovista1.entities.VisiteurInvite;

public class VisiteurMapper {
    public  static VisiteurInviteDto mapToVisiteurInviteDto(VisiteurInvite visiteurInvite){
        return new VisiteurInviteDto(
                visiteurInvite.getId(),
                visiteurInvite.getNom(),
                visiteurInvite.getPrenom(),
                visiteurInvite.getEmail(),
                visiteurInvite.getPhone(),
                visiteurInvite.getAdresse()
        );
    }
    public  static VisiteurInvite mapToVisiteurInviteDto(VisiteurInviteDto visiteurInviteDto){
        return new VisiteurInvite(
                visiteurInviteDto.getId(),
                visiteurInviteDto.getNom(),
                visiteurInviteDto.getPrenom(),
                visiteurInviteDto.getEmail(),
                visiteurInviteDto.getPhone(),
                visiteurInviteDto.getAdresse(),
                null,
                null
        );
    }

}
