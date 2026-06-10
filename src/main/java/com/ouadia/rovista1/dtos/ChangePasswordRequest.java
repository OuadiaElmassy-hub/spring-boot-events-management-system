package com.ouadia.rovista1.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ChangePasswordRequest{
    @NotBlank String currentPassword;
    @Size (min = 8) String newPassword;
    @NotBlank String confirmPassword;
}