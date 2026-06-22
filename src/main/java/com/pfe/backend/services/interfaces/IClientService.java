package com.pfe.backend.services.interfaces;


import com.pfe.backend.dtos.client.ClientPublicInfoResponseDto;
import com.pfe.backend.dtos.client.ClientRequestDto;
import com.pfe.backend.dtos.client.ClientResponseDto;
import com.pfe.backend.entities.Client;
import com.pfe.backend.exceptions.ClientNotFoundException;

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

    ClientPublicInfoResponseDto getInfoClientForPublic(Long id) throws ClientNotFoundException;

    Long getIdClientByUsername(String username) throws ClientNotFoundException;

}
