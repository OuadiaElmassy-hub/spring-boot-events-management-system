package com.ouadia.rovista1.services.interfaces;


import com.ouadia.rovista1.dtos.FavorieDto;
import com.ouadia.rovista1.entities.Favorie;
import com.ouadia.rovista1.exceptions.ClientNotFoundException;
import com.ouadia.rovista1.exceptions.EventNotFoundException;
import com.ouadia.rovista1.exceptions.FavorieNotFoundException;

import java.util.List;
import java.util.Map;

public interface IFavorieService {
    public FavorieDto addFavorie(FavorieDto favorieDto , Long IdClient, Long  IdEvent)throws FavorieNotFoundException, EventNotFoundException, ClientNotFoundException;
    public FavorieDto addEvenementAuFavorie(FavorieDto favorieDto,Long idEvent)throws FavorieNotFoundException,EventNotFoundException;
    public FavorieDto editFavorie(FavorieDto favorieDto ,Long id);
    public FavorieDto editFavorieMap(Long id , Map<String,Object> map);
    public FavorieDto getFavorieByIdClient(Long idClient)throws ClientNotFoundException;
    public List<FavorieDto> getAllFavories();
    public void deleteFavorieById(Long id);
    public void deleteAllByIds(Long ... ids);

}
