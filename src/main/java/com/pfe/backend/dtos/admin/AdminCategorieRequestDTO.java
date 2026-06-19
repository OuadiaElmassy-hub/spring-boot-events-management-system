package com.pfe.backend.dtos.admin;

import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AdminCategorieRequestDTO {
        private String nom;
        private String description;
        private String couleur;
        // Le fichier icone est reçu en @RequestParam MultipartFile
}