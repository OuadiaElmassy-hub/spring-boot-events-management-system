package com.pfe.backend.mappers;

import com.pfe.backend.dtos.image.ImageRequestDto;
import com.pfe.backend.dtos.image.ImageResponseDto;
import com.pfe.backend.entities.Image;
import com.pfe.backend.exceptions.EventNotFoundException;
import com.pfe.backend.services.implementations.EventServiceImpl;
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
    public ImageResponseDto mappingImageToImageDtoResponse(Image e){
        return ImageResponseDto.builder()
                .nom(e.getNom())
                .url(e.getUrl())
                .evenementId(e.getEvenement().getId())
                .build();
    }
}
