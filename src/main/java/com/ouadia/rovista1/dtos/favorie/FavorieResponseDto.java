package com.ouadia.rovista1.dtos.favorie;



import com.ouadia.rovista1.entities.Client;
import com.ouadia.rovista1.entities.Evenement;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class FavorieResponseDto {
    private  Long id;
    private String description;
    private LocalDateTime dateCreation;
    private Long clientId;
    private List<Long> evenementsId;
}
