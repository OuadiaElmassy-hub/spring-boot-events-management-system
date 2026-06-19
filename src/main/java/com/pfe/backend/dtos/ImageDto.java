package com.pfe.backend.dtos;

import com.pfe.backend.entities.Evenement;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data // pour les methode getter, setter, toString() , hachcode() ,equals()
@NoArgsConstructor @AllArgsConstructor
@Builder
public class ImageDto {



    private Long id;
    @NotNull
    private String nom;
    @NotNull
    private String url;
    @NotNull
    private String type;
    @NotNull
    private Evenement evenement;


    public ImageDto(Long id, String nom, String url, String type) {
        this.id = id;
        this.nom = nom;
        this.url = url;
        this.type = type;
    }
}
