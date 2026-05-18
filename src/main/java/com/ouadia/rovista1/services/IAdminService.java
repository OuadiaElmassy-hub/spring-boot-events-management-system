package com.ouadia.rovista1.services;


import com.ouadia.rovista1.entities.Admin;

import java.util.List;

public interface IAdminService {
    public Admin addAdmin(Admin admin);
    public Admin editAdmin(Admin admin);
    public Admin getAdminById(Long id);
    public List<Admin> getAllAdmins();
    public void deleteAdminById(Long id);
}
