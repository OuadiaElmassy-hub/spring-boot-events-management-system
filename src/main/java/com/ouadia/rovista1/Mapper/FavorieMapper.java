package com.ouadia.rovista1.Mapper;

import com.ouadia.rovista1.dtos.FavorieDto;
import com.ouadia.rovista1.entities.Favorie;

public class FavorieMapper {
    public static FavorieDto mapToFavorieDto(Favorie favorie){

        return new FavorieDto(
                favorie.getId(),
                favorie.getDescription(),
                favorie.getDateCreation()
        );
    }

    public static Favorie mapToFavorie(FavorieDto favorieDto){

        return new Favorie(
                favorieDto.getId(),
                favorieDto.getDescription(),
                favorieDto.getDateCreation(),
                null,
                null
        );
    }
}
