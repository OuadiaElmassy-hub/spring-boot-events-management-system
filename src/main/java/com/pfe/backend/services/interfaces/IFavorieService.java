package com.pfe.backend.services.interfaces;


import com.pfe.backend.dtos.favorie.FavorieRequestDto;
import com.pfe.backend.dtos.favorie.FavorieResponseDto;
import com.pfe.backend.dtos.favorie.HistoriqueFavorieDto;
import com.pfe.backend.exceptions.BusinessException;
import com.pfe.backend.exceptions.ClientNotFoundException;
import com.pfe.backend.exceptions.EventNotFoundException;
import com.pfe.backend.exceptions.FavorieNotFoundException;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;

public interface IFavorieService {
    public FavorieResponseDto addFavorie(Long IdClient, Long  IdEvent) throws FavorieNotFoundException, EventNotFoundException, ClientNotFoundException, BusinessException;
    public FavorieResponseDto addEvenementAuFavorie(Long idFavorie,Long idEvent,String newDescription)throws FavorieNotFoundException,EventNotFoundException;
    public FavorieResponseDto editFavorie(FavorieRequestDto favorieDto ,Long id);
    public FavorieResponseDto editFavorieMap(Long id , Map<String,Object> map);
    public FavorieResponseDto getFavorieByIdClient(Long idClient)throws ClientNotFoundException;
    public List<FavorieResponseDto> getAllFavories();
    public void deleteFavorieById(Long id);
    public void deleteAllByIds(Long ... ids);

    Page<HistoriqueFavorieDto> getFavories(Long clientId, int page, int size);

    void removeFavorie(Long userId, Long eventId);
}
