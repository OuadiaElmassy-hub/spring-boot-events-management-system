package com.pfe.backend.services.interfaces;


import com.pfe.backend.dtos.PageResponse;
import com.pfe.backend.dtos.avis.AvisRequestDto;
import com.pfe.backend.dtos.avis.AvisResponseDto;
import com.pfe.backend.exceptions.AvisNotFoundException;
import com.pfe.backend.exceptions.EventNotFoundException;


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
    AvisResponseDto addAvisClient(Long clientId, Long evenementId, double note, String comment);
}

