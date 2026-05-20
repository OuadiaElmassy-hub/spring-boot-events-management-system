package com.ouadia.rovista1.services.implementations;

import com.ouadia.rovista1.entities.VisiteurInvite;
import com.ouadia.rovista1.repositories.VisiteurRepository;
import com.ouadia.rovista1.services.interfaces.IVisiteurService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class VisiteurInviteServiceImpl implements IVisiteurService {

    private VisiteurRepository repository;

    @Override
    public VisiteurInvite addVisiteurInvite(VisiteurInvite visiteurInvite) {
        return repository.save(visiteurInvite);
    }

    @Override
    public VisiteurInvite editVisiteurInvite(VisiteurInvite visiteurInvite) {
        return repository.save(visiteurInvite);
    }

    @Override
    public VisiteurInvite getVisiteurInviteById(Long id) {
        return repository.findById(id).orElseThrow();
    }

    @Override
    public List<VisiteurInvite> getAllVisiteurInvites() {
        return repository.findAll();
    }

    @Override
    public void deleteVisiteurInviteById(Long id) {
        repository.deleteById(id);
    }
}
