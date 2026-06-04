package com.ouadia.rovista1.dtos.avis;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data // pour les methode getter, setter, toString() , hachcode() ,equals()
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AvisRequestDto {
    private String comment;
    private double note;
    private LocalDate dateAvis;
}
