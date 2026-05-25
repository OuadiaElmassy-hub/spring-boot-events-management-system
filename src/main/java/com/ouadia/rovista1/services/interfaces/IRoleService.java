package com.ouadia.rovista1.services.interfaces;


import com.ouadia.rovista1.dtos.RoleDto;
import com.ouadia.rovista1.entities.Role;
import com.ouadia.rovista1.exceptions.RoleNotFoundException;

import java.util.List;
import java.util.Map;

public interface IRoleService {
    public RoleDto addRole(RoleDto roleDto);
    public RoleDto editRole(RoleDto roleDto ,  Integer id);
    public RoleDto editRoleMap(  Integer id , Map<String,Object> map);
    public RoleDto getRoleById(  Integer id )throws RoleNotFoundException;
    public List<RoleDto> getAllRoles();
    public void deleteRoleById( Integer id);
    public void deleteAllByIds(Integer ... ids);
}
