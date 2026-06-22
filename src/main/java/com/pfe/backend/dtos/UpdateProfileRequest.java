package com.pfe.backend.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UpdateProfileRequest{
    @NotBlank
    private String nom;
    @NotBlank
    private String prenom;
    @Email
    private String email;
    @NotBlank
    private String telephone;
    private String ville;
}