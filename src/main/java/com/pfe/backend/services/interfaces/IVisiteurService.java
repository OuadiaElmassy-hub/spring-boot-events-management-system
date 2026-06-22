package com.pfe.backend.services.interfaces;


import com.pfe.backend.dtos.visiteur.VisiteurInviteRequestDto;
import com.pfe.backend.dtos.visiteur.VisiteurInviteResponseDto;
import com.pfe.backend.exceptions.UserNotFoundException;

import java.util.List;
import java.util.Map;

public interface IVisiteurService {
    public VisiteurInviteResponseDto addVisiteur(VisiteurInviteRequestDto visiteurInviteDto);
    public VisiteurInviteResponseDto editVisiteur(VisiteurInviteRequestDto visiteurInviteDto ,  Long id);
    public VisiteurInviteResponseDto editVisiteurMap(  Long id , Map<String,Object> map);
    public VisiteurInviteResponseDto getVisiteurById(  Long id )throws UserNotFoundException;
    public List<VisiteurInviteResponseDto> getAllVisiteurs();
    public void deleteVisiteurById( Long id);
    public void deleteAllByIds(Long ... ids);
}
