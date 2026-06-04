package com.ouadia.rovista1.mappers;

import com.ouadia.rovista1.dtos.billet.BilletRequestDto;
import com.ouadia.rovista1.dtos.billet.BilletResponseDto;
import com.ouadia.rovista1.entities.Billet;
import com.ouadia.rovista1.exceptions.BilletNotFoundException;
import com.ouadia.rovista1.exceptions.ReservationNotFoundException;
import com.ouadia.rovista1.services.implementations.ImageServiceImpl;
import com.ouadia.rovista1.services.implementations.ReservationServiceImpl;
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
    public static BilletResponseDto mappingBilletToBilletDtoResponse(Billet e){
        return  BilletResponseDto.builder()
                .code(e.getCode())
                .qrCode(e.getQrCode())
                .dateBillet(e.getDateBillet())
                .type(e.getType())
                .build();

    }
}
