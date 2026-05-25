package com.ouadia.rovista1.Mapper;

import com.ouadia.rovista1.dtos.ClientDto;
import com.ouadia.rovista1.entities.Client;

public class ClientMapper {
    public static ClientDto mapToClientDto(Client client){

        return new ClientDto(
                client.getId(),
                client.getUsername(),
                client.getEmail(),
                client.getMotDePasse(),
                client.getStatutCompte(),
                client.getPhone(),
                client.getAdresse(),
                client.getNom(),
                client.getPrenom(),
                client.getDateNaissance()
        );
    }

    public static Client mapToClient(ClientDto clientDto){

        return new Client(
                clientDto.getId(),
                clientDto.getUsername(),
                clientDto.getEmail(),
                clientDto.getMotDePasse(),
                clientDto.getStatutCompte(),
                clientDto.getPhone(),
                clientDto.getAdresse(),
                clientDto.getNom(),
                clientDto.getPrenom(),
                clientDto.getDateNaissance(),
                null,
                null,
                null,
                null
        );
    }
}
