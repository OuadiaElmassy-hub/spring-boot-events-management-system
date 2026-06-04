package com.ouadia.rovista1.dtos.image;

import com.ouadia.rovista1.entities.Evenement;
import com.ouadia.rovista1.entities.enums.TypePhoto;
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
