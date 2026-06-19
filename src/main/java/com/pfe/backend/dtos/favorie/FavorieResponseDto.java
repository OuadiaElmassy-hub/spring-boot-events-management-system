package com.pfe.backend.dtos.favorie;



import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Builder
public class FavorieResponseDto {
    private  Long id;
    private String description;
    private LocalDateTime dateCreation;
    private Long clientId;
    private Long evenementId;
}
