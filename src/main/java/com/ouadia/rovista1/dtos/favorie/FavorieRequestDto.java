package com.ouadia.rovista1.dtos.favorie;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
@Data
@AllArgsConstructor
@Builder
public class FavorieRequestDto {

    private String description;
    private LocalDateTime dateCreation;
    private Long ClientId;
}
