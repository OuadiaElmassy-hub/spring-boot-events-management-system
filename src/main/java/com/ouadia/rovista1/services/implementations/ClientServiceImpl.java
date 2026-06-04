package com.ouadia.rovista1.services.implementations;

import com.ouadia.rovista1.dtos.client.ClientRequestDto;
import com.ouadia.rovista1.dtos.client.ClientResponseDto;
import com.ouadia.rovista1.entities.Client;
import com.ouadia.rovista1.entities.Notification;
import com.ouadia.rovista1.entities.Role;
import com.ouadia.rovista1.entities.enums.StatutCompte;
import com.ouadia.rovista1.mappers.ClientMapper;
import com.ouadia.rovista1.repositories.ClientRepository;
import com.ouadia.rovista1.services.interfaces.IClientService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Service
@Transactional
@AllArgsConstructor
public class ClientServiceImpl implements IClientService {

    private ClientRepository repository;

    @Override
    public ClientResponseDto addClient(ClientRequestDto clientDto) {
        Client client= ClientMapper.mappingClientDtoRequestToClient(clientDto);
            return ClientMapper.mappingClientToClientDtoResponse(repository.save(client));
    }

    @Override
    public ClientResponseDto editClient(ClientRequestDto clientDto ,Long idReche) {
        Client client= ClientMapper.mappingClientDtoRequestToClient(clientDto);
        if (client == null) return null;
        else {
            Client client1 = repository.findById(idReche).get();
            if (client1 == null) {return null;}
            client1.setUsername(client.getUsername());
            client1.setEmail(client.getEmail());
            client1.setMotDePasse(client.getMotDePasse());
            client1.setStatutCompte(client.getStatutCompte());
            client1.setPhone(client.getPhone());
            client1.setAdresse(client.getAdresse());
            client1.setNotifications(client.getNotifications());
            client1.setRoles(client.getRoles());
            client1.setNom(client.getNom());
            client1.setPrenom(client.getPrenom());
            client1.setDateNaissance(client.getDateNaissance());
            return ClientMapper.mappingClientToClientDtoResponse(repository.save(client1));
        }
    }

    @Override
    public ClientResponseDto editClientMap(Long idReche, Map<String, Object> map) {
        if (map == null ) return null;
        else {
            Client client1 = repository.findById(idReche).get();
            if (client1 == null) {return null;}
            if (map.containsKey("username")){
                client1.setUsername((String) map.get("username"));
            }
            if (map.containsKey("email")){
                client1.setEmail((String) map.get("email"));
            }
            if (map.containsKey("motDePasse")){
                client1.setMotDePasse((String) map.get("motDePasse"));
            }
            if (map.containsKey("statutCompte")){
                client1.setStatutCompte(StatutCompte.valueOf(map.get("statutCompte").toString()));
            }
            if (map.containsKey("phone")) {
                client1.setPhone((String) map.get("phone"));
            }
            if (map.containsKey("adresse")) {
                client1.setAdresse((String) map.get("adresse"));
            }
            if (map.containsKey("notifications")) {
                client1.setNotifications((List<Notification>)map.get("notifications"));
            }
            if (map.containsKey("roles")) {
                client1.setRoles((List<Role>)map.get("roles"));
            }
            if (map.containsKey("nom")) {
                client1.setNom((String) map.get("nom"));
            }
            if (map.containsKey("prenom")) {
                client1.setPrenom((String) map.get("prenom"));
            }
            if (map.containsKey("dateNaissance")) {
                client1.setDateNaissance((LocalDate) map.get("dateNaissance"));
            }
            return ClientMapper.mappingClientToClientDtoResponse(repository.save(client1));
        }
    }


    @Override
    public ClientResponseDto getClientById(Long id) {
        return ClientMapper.mappingClientToClientDtoResponse(repository.findById(id)
                .orElseThrow(()->new RuntimeException("client not found")));
    }

    @Override
    public Client getClientEntityById(Long id) {
        return (repository.findById(id)
                .orElseThrow(()->new RuntimeException("client not found")));
    }

    @Override
    public List<ClientResponseDto> getAllClients() {
        return (repository.findAll().stream().map(client->ClientMapper.mappingClientToClientDtoResponse(client)).toList());
    }

    @Override
    public void deleteClientById(Long id) {
        repository.deleteById(id);
    }

    @Override
    public void deleteAllByIds(Long... ids) {
        for (Long id :ids){
            deleteClientById(id);
        }
    }
}
