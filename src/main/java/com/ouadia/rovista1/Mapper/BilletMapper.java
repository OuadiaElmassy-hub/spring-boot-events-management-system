package com.ouadia.rovista1.Mapper;

import com.ouadia.rovista1.dtos.BilletDto;
import com.ouadia.rovista1.entities.Billet;

public class BilletMapper {
    public static BilletDto mapToBilletDto(Billet billet){

        return new BilletDto(
                billet.getId(),
                billet.getCode(),
                billet.getQrCode(),
                billet.getDateBillet(),
                billet.getType()
        );
    }

    public static Billet mapToBillet(BilletDto billetDto){

        return new Billet(
                billetDto.getId(),
                billetDto.getCode(),
                billetDto.getQrCode(),
                billetDto.getDateBillet(),
                billetDto.getType(),
                null
        );
    }
}
