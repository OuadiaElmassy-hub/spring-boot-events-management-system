package com.ouadia.rovista1.services.interfaces;


import com.ouadia.rovista1.dtos.avis.AvisRequestDto;
import com.ouadia.rovista1.dtos.avis.AvisResponseDto;
import com.ouadia.rovista1.exceptions.AvisNotFoundException;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;

public interface IAvisService {
    public AvisResponseDto addAvisDto(AvisRequestDto avisDto);
    public AvisResponseDto editAvis(AvisRequestDto avisDto, Long idRrch);
    public AvisResponseDto editAvisMap(Long idRrch, Map<String,Object> map);
    public AvisResponseDto getAvisById(Long id)throws AvisNotFoundException;

    public List<AvisResponseDto> getAllAvis();
    public void deleteAvisById(Long id);
    public void deleteAllByIds(Long ... ids);
    List<AvisResponseDto> getAvisByEvenementId(Long evenementId);
    }