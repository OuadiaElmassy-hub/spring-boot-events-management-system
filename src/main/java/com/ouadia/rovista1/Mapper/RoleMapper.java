package com.ouadia.rovista1.Mapper;

import com.ouadia.rovista1.dtos.RoleDto;
import com.ouadia.rovista1.entities.Role;

public class RoleMapper {
    public static RoleDto mapToRoleDto(Role role) {

        return new RoleDto(
                role.getId(),
                role.getRoleName()
        );
    }

    public static Role mapToRole(RoleDto dto) {

        return new Role(
                dto.getId(),
                dto.getRoleName()
        );
    }
}
