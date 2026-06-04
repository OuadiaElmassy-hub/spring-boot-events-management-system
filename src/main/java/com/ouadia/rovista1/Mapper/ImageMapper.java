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

    public Image mapToImage(ImageDto imageDto){

         new Image();
         return Image.builder()
                 .nom(imageDto.getNom())
                 .url(imageDto.getUrl())
                 .type(imageDto.getType())
                 .build();
    }

}
