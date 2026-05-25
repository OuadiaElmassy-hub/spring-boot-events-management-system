package com.ouadia.rovista1.services.interfaces;


import com.ouadia.rovista1.dtos.AvisDto;
import com.ouadia.rovista1.entities.Avis;
import com.ouadia.rovista1.exceptions.AvisNotFoundException;

import java.util.List;
import java.util.Map;

public interface IAvisService {
    public AvisDto addAvisDto(AvisDto avisDto);
    public AvisDto editAvis(AvisDto avisDto, Long idRrch);
    public AvisDto editAvisMap(Long idRrch, Map<String,Object> map);
    public AvisDto getAvisById(Long id)throws AvisNotFoundException;
    public List<AvisDto> getAllAvis();
    public void deleteAvisById(Long id);
    public void deleteAllByIds(Long ... ids);
}
