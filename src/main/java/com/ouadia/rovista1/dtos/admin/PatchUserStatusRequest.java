package com.ouadia.rovista1.dtos.admin;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class PatchUserStatusRequest {
    @NotBlank
    private String status;  // "Actif" / "Inactif"
}