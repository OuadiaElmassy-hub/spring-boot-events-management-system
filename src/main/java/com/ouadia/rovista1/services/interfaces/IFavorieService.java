package com.ouadia.rovista1.services.interfaces;


import com.ouadia.rovista1.dtos.favorie.FavorieRequestDto;
import com.ouadia.rovista1.dtos.favorie.FavorieResponseDto;
import com.ouadia.rovista1.dtos.favorie.HistoriqueFavorieDto;
import com.ouadia.rovista1.exceptions.BusinessException;
import com.ouadia.rovista1.exceptions.ClientNotFoundException;
import com.ouadia.rovista1.exceptions.EventNotFoundException;
import com.ouadia.rovista1.exceptions.FavorieNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

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
