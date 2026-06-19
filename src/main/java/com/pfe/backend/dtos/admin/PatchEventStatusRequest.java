package com.pfe.backend.dtos.admin;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class PatchEventStatusRequest{
    @NotBlank
    private String status;   // "Approuvé" / "Suspendu" / "Rejeté"
    private String motif;
}