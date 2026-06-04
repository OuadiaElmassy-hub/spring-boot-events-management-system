package com.ouadia.rovista1.services.interfaces;


import com.ouadia.rovista1.dtos.client.ClientRequestDto;
import com.ouadia.rovista1.dtos.client.ClientResponseDto;
import com.ouadia.rovista1.entities.Client;

import java.util.List;
import java.util.Map;

public interface IClientService {
    public ClientResponseDto addClient(ClientRequestDto clientDto);
    public ClientResponseDto editClient(ClientRequestDto clientDto ,Long idRech);
    public ClientResponseDto editClientMap(Long idReche , Map<String,Object> map);
    public ClientResponseDto getClientById(Long id);
    public Client getClientEntityById(Long id);
    public List<ClientResponseDto> getAllClients();
    public void deleteClientById(Long id);
    public void deleteAllByIds(Long ... ids);
}
