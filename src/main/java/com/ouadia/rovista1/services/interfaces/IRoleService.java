package com.ouadia.rovista1.services.interfaces;


import com.ouadia.rovista1.entities.Role;

import java.util.List;

public interface IRoleService {
    public Role addRole(Role role);
    public Role editRole(Role role);
    public Role getRoleById(Long id);
    public List<Role> getAllRoles();
    public void deleteRoleById(Long id);
}
