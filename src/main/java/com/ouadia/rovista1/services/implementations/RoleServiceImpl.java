package com.ouadia.rovista1.services.implementations;


import com.ouadia.rovista1.Mapper.RoleMapper;

import com.ouadia.rovista1.dtos.RoleDto;
import com.ouadia.rovista1.entities.*;
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
    public RoleDto addRole(RoleDto roleDto) {
        Role role= RoleMapper.mapToRole(roleDto);
        if (repository.existsById(role.getId())){
            throw new RuntimeException(" role not exsist ");
        }else
            return RoleMapper.mapToRoleDto(repository.save(role));
    }

    @Override
    public RoleDto editRole(RoleDto roleDto, Integer id) {
        Role role= RoleMapper.mapToRole(roleDto);
        if (role==null)return null;
        else {
            Role role1 =repository.findById(id).get();
            if (role1==null)return null;
            role1.setRoleName(role.getRoleName());
            return RoleMapper.mapToRoleDto(repository.save(role1));
        }
    }

    @Override
    public RoleDto editRoleMap(Integer id, Map<String, Object> map) {
        if (map == null ) return null;
        else {
            Role role1 = repository.findById(id).get();
            if (role1 == null) {
                return null;
            }
            if (map.containsKey("roleName")) {
                role1.setRoleName((String) map.get("roleName"));
            }
            return RoleMapper.mapToRoleDto(repository.save(role1));
        }
    }

    @Override
    public RoleDto getRoleById(Integer id) throws RoleNotFoundException {
        Role role = repository.findById(id).orElseThrow(() -> new RoleNotFoundException("role not found"));
        return RoleMapper.mapToRoleDto(role);
    }

    @Override
    public List<RoleDto> getAllRoles() {
        return (repository.findAll().stream().map(role-> RoleMapper.mapToRoleDto(role)).toList());

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
