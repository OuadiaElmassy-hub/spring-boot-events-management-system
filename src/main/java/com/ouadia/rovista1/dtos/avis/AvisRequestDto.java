package com.ouadia.rovista1.dtos.avis;

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
