package com.ouadia.rovista1.services.interfaces;


import com.ouadia.rovista1.entities.VisiteurInvite;

import java.util.List;

public interface IVisiteurService {
    public VisiteurInvite addVisiteurInvite(VisiteurInvite visiteurInvite);
    public VisiteurInvite editVisiteurInvite(VisiteurInvite visiteurInvite);
    public VisiteurInvite getVisiteurInviteById(Long id);
    public List<VisiteurInvite> getAllVisiteurInvites();
    public void deleteVisiteurInviteById(Long id);
}
