package com.ouadia.rovista1.services.interfaces;


import com.ouadia.rovista1.dtos.visiteur.VisiteurInviteRequestDto;
import com.ouadia.rovista1.dtos.visiteur.VisiteurInviteResponseDto;
import com.ouadia.rovista1.exceptions.UserNotFoundException;

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
