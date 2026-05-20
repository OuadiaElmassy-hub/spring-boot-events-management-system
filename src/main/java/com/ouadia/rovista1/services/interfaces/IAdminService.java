package com.ouadia.rovista1.services.interfaces;


import com.ouadia.rovista1.entities.Admin;

import java.util.List;
import java.util.Map;

public interface IAdminService {
    public Admin addAdmin(Admin admin);
    public Admin editAdmin(Admin admin ,long idRech);
    public Admin editAdmin(long idReche , Map<String,Object> map);
    public Admin getAdminById(Long id);
    public List<Admin> getAllAdmins();
    public void deleteAdminById(Long id);
    public void deleteAllByIds(Long ... ids);
}
