package com.ouadia.rovista1.services.interfaces;


import com.ouadia.rovista1.dtos.VisiteurInviteDto;
import com.ouadia.rovista1.entities.VisiteurInvite;
import com.ouadia.rovista1.exceptions.UserNotFoundException;

import java.util.List;
import java.util.Map;

public interface IVisiteurService {
    public VisiteurInviteDto addVisiteur(VisiteurInviteDto visiteurInviteDto);
    public VisiteurInviteDto editVisiteur(VisiteurInviteDto visiteurInviteDto ,  Long id);
    public VisiteurInviteDto editVisiteurMap(  Long id , Map<String,Object> map);
    public VisiteurInviteDto getVisiteurById(  Long id )throws UserNotFoundException;
    public List<VisiteurInviteDto> getAllVisiteurs();
    public void deleteVisiteurById( Long id);
    public void deleteAllByIds(Long ... ids);
}
