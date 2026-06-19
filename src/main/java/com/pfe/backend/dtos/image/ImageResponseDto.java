package com.pfe.backend.dtos.image;

import com.pfe.backend.entities.enums.TypePhoto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class ImageResponseDto {
    private Long id;
    private String nom;
    private String url;
    private TypePhoto type;
    private Long evenementId;
}
