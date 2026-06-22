package com.pfe.backend.dtos.avis;

import lombok.*;

import java.time.LocalDate;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AvisRequestDto {
    private String comment;
    private double note;
}
