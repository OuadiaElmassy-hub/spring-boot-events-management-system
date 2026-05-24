package com.ouadia.rovista1.dtos;

public class RoleDto {

    private int id;

    private String roleName;

    public RoleDto() {
    }

    public RoleDto(int id, String roleName) {
        this.id = id;
        this.roleName = roleName;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }
    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
}
