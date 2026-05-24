package com.ouadia.rovista1.services.interfaces;


import com.ouadia.rovista1.dtos.AdminDto;

import java.util.List;
import java.util.Map;

public interface IAdminService {
    public AdminDto addAdmin(AdminDto adminDto);
    public AdminDto editAdmin(AdminDto adminDto ,Long idRech);
    public AdminDto editAdminMap(Long idReche , Map<String,Object> map);
    public AdminDto getAdminById(Long id);
    public List<AdminDto> getAllAdmins();
    public void deleteAdminById(Long id);
    public void deleteAllByIds(Long ... ids);
}
