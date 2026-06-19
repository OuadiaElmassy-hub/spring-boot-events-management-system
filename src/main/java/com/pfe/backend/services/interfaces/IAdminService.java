package com.pfe.backend.services.interfaces;


import com.pfe.backend.dtos.AdminDto;

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
