package com.ouadia.rovista1.services.interfaces;



import com.ouadia.rovista1.entities.Role;
import com.ouadia.rovista1.exceptions.RoleNotFoundException;

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
