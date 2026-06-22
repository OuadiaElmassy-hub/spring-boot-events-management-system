package com.pfe.backend.mappers;

import com.pfe.backend.dtos.billet.BilletRequestDto;
import com.pfe.backend.dtos.billet.BilletResponseDto;
import com.pfe.backend.entities.Billet;
import com.pfe.backend.exceptions.ReservationNotFoundException;
import com.pfe.backend.services.implementations.ImageServiceImpl;
import com.pfe.backend.services.implementations.ReservationServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class  BilletMapper {

    private ImageServiceImpl imageService;
    private ReservationServiceImpl reservationService;

    public  Billet mappingBilletDtoRequestToBillet(BilletRequestDto dto)throws ReservationNotFoundException{
        return Billet.builder()
                .code(dto.getCode())
                .qrCode(dto.getQrCode())
                .dateBillet(dto.getDateBillet())
                .type(dto.getType())
                .reservation(reservationService.getReservationEntityById(dto.getReservationId()))
                .build();
    }
    public BilletResponseDto mappingBilletToBilletDtoResponse(Billet e){
        return  BilletResponseDto.builder()
                .code(e.getCode())
                .qrCode(e.getQrCode())
                .dateBillet(e.getDateBillet())
                .type(e.getType())
                .build();
    }
}
