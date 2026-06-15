package com.ouadia.rovista1.dtos.categorie;

import lombok.*;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategorieResponseDto {

    private Long id;
    private String nom;
    private String description;
    private String iconUrl;

}
