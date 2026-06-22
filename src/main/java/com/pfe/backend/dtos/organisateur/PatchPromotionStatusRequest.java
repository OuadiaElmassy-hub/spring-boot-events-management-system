package com.pfe.backend.dtos.organisateur;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PatchPromotionStatusRequest {
    
    @NotBlank
    String status;
}