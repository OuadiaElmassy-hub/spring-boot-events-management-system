package com.ouadia.rovista1.mappers;

import com.ouadia.rovista1.dtos.billet.BilletRequestDto;
import com.ouadia.rovista1.dtos.billet.BilletResponseDto;
import com.ouadia.rovista1.entities.Billet;
import com.ouadia.rovista1.services.implementations.ImageServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class BilletMapper {

    private ImageServiceImpl imageService;

    public static Billet mappingBilletDtoRequestToBillet(BilletRequestDto dto){
        return new Billet();
    }
    public static BilletResponseDto mappingBilletToBilletDtoResponse(Billet e){
        return new BilletResponseDto();
    }
}
