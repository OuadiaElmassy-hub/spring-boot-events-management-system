package com.ouadia.rovista1.dtos.organisateur;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PatchEventStatusRequest {
    
    @NotBlank
    String status;
}