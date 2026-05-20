package com.ouadia.rovista1.services.implementations;

import com.ouadia.rovista1.entities.Role;
import com.ouadia.rovista1.repositories.RoleRepository;
import com.ouadia.rovista1.services.interfaces.IRoleService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class RoleServiceImpl implements IRoleService {

    private RoleRepository repository;


    @Override
    public Role addRole(Role role) {
        return repository.save(role);
    }

    @Override
    public Role editRole(Role role) {
        return repository.save(role);
    }

    @Override
    public Role getRoleById(Long id) {
        return repository.findById(id).orElseThrow();
    }

    @Override
    public List<Role> getAllRoles() {
        return repository.findAll();
    }

    @Override
    public void deleteRoleById(Long id) {
        repository.deleteById(id);
    }
}
