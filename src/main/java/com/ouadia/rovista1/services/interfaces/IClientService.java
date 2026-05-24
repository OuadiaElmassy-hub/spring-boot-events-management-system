package com.ouadia.rovista1.services.interfaces;


import com.ouadia.rovista1.dtos.ClientDto;
import com.ouadia.rovista1.dtos.ClientDto;
import com.ouadia.rovista1.entities.Client;

import java.util.List;
import java.util.Map;

public interface IClientService {
    public ClientDto addClient(ClientDto clientDto);
    public ClientDto editClient(ClientDto clientDto ,Long idRech);
    public ClientDto editClientMap(Long idReche , Map<String,Object> map);
    public ClientDto getClientById(Long id);
    public List<ClientDto> getAllClients();
    public void deleteClientById(Long id);
    public void deleteAllByIds(Long ... ids);
}
