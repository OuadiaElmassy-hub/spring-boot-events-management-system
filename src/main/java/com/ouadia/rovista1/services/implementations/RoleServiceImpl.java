package com.ouadia.rovista1.services.implementations;


import com.ouadia.rovista1.entities.Role;

import com.ouadia.rovista1.exceptions.RoleNotFoundException;
import com.ouadia.rovista1.repositories.RoleRepository;
import com.ouadia.rovista1.services.interfaces.IRoleService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Map;

@Service
@Transactional
@AllArgsConstructor
public class RoleServiceImpl implements IRoleService {

    private RoleRepository repository;


    @Override
    public Role addRole(Role role) {

        return (repository.save(role));
    }

    @Override
    public Role editRole(Role role, Integer id) {
        if (role==null)return null;
        else {
            Role role1 =repository.findById(id).get();
            if (role1==null)return null;
            role1.setRoleName(role.getRoleName());
            return (repository.save(role1));
        }
    }

    @Override
    public Role editRoleMap(Integer id, Map<String, Object> map) {
        if (map == null ) return null;
        else {
            Role role1 = repository.findById(id).get();
            if (role1 == null) {
                return null;
            }
            if (map.containsKey("roleName")) {
                role1.setRoleName((String) map.get("roleName"));
            }
            return (repository.save(role1));
        }
    }

    @Override
    public Role getRoleById(Integer id) throws RoleNotFoundException {
        Role role = repository.findById(id).orElseThrow(() -> new RoleNotFoundException("role not found"));
        return (role);
    }

    @Override
    public List<Role> getAllRoles() {
        return (repository.findAll().stream().toList());

    }

    @Override
    public void deleteRoleById(Integer id) {
        repository.deleteById(id);
    }

    @Override
    public void deleteAllByIds(Integer ... ids) {
        for ( Integer id :ids){
            deleteRoleById(id);
        }
    }
}
