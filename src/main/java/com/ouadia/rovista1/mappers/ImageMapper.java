package com.ouadia.rovista1.mappers;

import com.ouadia.rovista1.dtos.image.ImageRequestDto;
import com.ouadia.rovista1.dtos.image.ImageResponseDto;
import com.ouadia.rovista1.entities.Image;
import com.ouadia.rovista1.exceptions.EventNotFoundException;
import com.ouadia.rovista1.services.implementations.EventServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ImageMapper {
  EventServiceImpl eventService;

    public Image mappingImageDtoRequestToImage(ImageRequestDto dto) throws EventNotFoundException {
        return Image.builder()
                .nom(dto.getNom())
                .url(dto.getUrl())
                .evenement(eventService.getEvenementEntityById(dto.getEvenementId()))
                .build();
    }
    public static ImageResponseDto mappingImageToImageDtoResponse(Image e){
        return ImageResponseDto.builder()
                .nom(e.getNom())
                .url(e.getUrl())
                .evenementId(e.getEvenement().getId())
                .build();
    }
}
