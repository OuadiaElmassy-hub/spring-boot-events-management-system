package com.ouadia.rovista1.mappers;


import com.ouadia.rovista1.dtos.visiteur.VisiteurInviteRequestDto;
import com.ouadia.rovista1.dtos.visiteur.VisiteurInviteResponseDto;
import com.ouadia.rovista1.entities.Avis;
import com.ouadia.rovista1.entities.Reservation;
import com.ouadia.rovista1.entities.VisiteurInvite;
import org.springframework.stereotype.Component;


@Component
public class VisiteurMapper {

    public VisiteurInvite mappingVisiteurInviteDtoRequestToVisiteurInvite(VisiteurInviteRequestDto dto){

        return VisiteurInvite.builder()
                .nom(dto.getNom())
                .prenom(dto.getPrenom())
                .email(dto.getEmail())
                .phone(dto.getPhone())

                .build();
    }
    public VisiteurInviteResponseDto mappingVisiteurInviteToVisiteurInviteDtoResponse(VisiteurInvite e){
        return VisiteurInviteResponseDto.builder()
                .nom(e.getNom())
                .prenom(e.getPrenom())
                .email(e.getEmail())
                .phone(e.getPhone())
                .adresse(e.getAdresse())
                .reservationsId(e.getReservations().stream().map(Reservation::getId).toList())
                .avisId(e.getAvis().stream().map(Avis::getId).toList())
                .build();
    }

}
