package com.ouadia.rovista1.Mapper;

import com.ouadia.rovista1.dtos.ImageDto;
import com.ouadia.rovista1.entities.Image;

public class ImageMapper {
    public static ImageDto mapToImageDto(Image image){

        return new ImageDto(
                image.getId(),
                image.getNom(),
                image.getUrl(),
                image.getType()
        );
    }

    public static Image mapToImage(ImageDto imageDto){

        return new Image(
                imageDto.getId(),
                imageDto.getNom(),
                imageDto.getUrl(),
                imageDto.getType(),
                null
        );
    }

}
