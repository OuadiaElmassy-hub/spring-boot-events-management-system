package com.pfe.backend.dtos.categorie;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CategorieRequestDto {
    @NotNull
    private String nom;
    @NotNull
    private String description;
}
