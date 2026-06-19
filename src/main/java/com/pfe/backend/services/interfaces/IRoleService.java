package com.pfe.backend.services.interfaces;



import com.pfe.backend.entities.Role;
import com.pfe.backend.exceptions.RoleNotFoundException;

import java.util.List;
import java.util.Map;

public interface IRoleService {
    public Role addRole(Role role);
    public Role editRole(Role role ,  Integer id);
    public Role editRoleMap(  Integer id , Map<String,Object> map);
    public Role getRoleById(  Integer id )throws RoleNotFoundException;
    public List<Role> getAllRoles();
    public void deleteRoleById( Integer id);
    public void deleteAllByIds(Integer ... ids);
}
