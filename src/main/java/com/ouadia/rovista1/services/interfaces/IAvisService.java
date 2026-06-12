package com.ouadia.rovista1.services.interfaces;


import com.ouadia.rovista1.dtos.PageResponse;
import com.ouadia.rovista1.dtos.avis.AvisRequestDto;
import com.ouadia.rovista1.dtos.avis.AvisResponseDto;
import com.ouadia.rovista1.exceptions.AvisNotFoundException;
import com.ouadia.rovista1.exceptions.EventNotFoundException;


import java.util.List;
import java.util.Map;

public interface IAvisService {
    AvisResponseDto addAvis(AvisRequestDto avisDto);
    AvisResponseDto editAvis(AvisRequestDto avisDto, Long idRrch);
    AvisResponseDto editAvisMap(Long idRrch, Map<String,Object> map);
    AvisResponseDto getAvisById(Long id)throws AvisNotFoundException;
    PageResponse<AvisResponseDto> getListAvisByEvenementId(int page, int size, Long id) throws EventNotFoundException;
    List<AvisResponseDto> getAllAvis();
    void deleteAvisById(Long id);
    void deleteAllByIds(Long ... ids);
}

