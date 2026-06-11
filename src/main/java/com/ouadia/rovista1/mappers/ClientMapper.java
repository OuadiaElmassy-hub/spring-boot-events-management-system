package com.ouadia.rovista1.mappers;



import com.ouadia.rovista1.dtos.client.ClientPublicInfoResponseDto;
import com.ouadia.rovista1.dtos.client.ClientRequestDto;
import com.ouadia.rovista1.dtos.client.ClientResponseDto;
import com.ouadia.rovista1.entities.*;
import org.springframework.stereotype.Component;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class ClientMapper {
    public Client mappingClientDtoRequestToClient(ClientRequestDto dto){
        return  Client.builder()
                .username(dto.getUsername())
                .email(dto.getEmail())
                .motDePasse(dto.getMotDePasse())
                .statutCompte(dto.getStatutCompte())
                .phone(dto.getPhone())
                .adresse(dto.getAdresse())
                .nom(dto.getNom())
                .prenom(dto.getPrenom())
                .dateNaissance(dto.getDateNaissance())
                .build();

    }
    public ClientResponseDto mappingClientToClientDtoResponse(Client e){
        return ClientResponseDto.builder()
                .username(e.getUsername())
                .email(e.getEmail())
                .statutCompte(e.getStatutCompte())
                .phone(e.getPhone())
                .adresse(e.getAdresse())
                .nom(e.getNom())
                .prenom(e.getPrenom())
                .dateNaissance(e.getDateNaissance())
                .promotionsId(e.getPromotions().stream().map(Promotion::getId).toList())
                .reservationsId(e.getReservations().stream().map(Reservation::getId).toList())
                .avisId(e.getAvis().stream().map(Avis::getId).toList())
                .favoritesId(e.getFavories().stream().map(Favorie::getId).toList())
                .build();
    }

    public ClientPublicInfoResponseDto mappingClientToClientPublicInfoResponseDto(Client e){
        return ClientPublicInfoResponseDto.builder()
                .email(e.getEmail())
                .phone(e.getPhone())
                .adresse(e.getAdresse())
                .nom(e.getNom())
                .prenom(e.getPrenom())
                .dateNaissance(e.getDateNaissance())
                .build();
    }
}
