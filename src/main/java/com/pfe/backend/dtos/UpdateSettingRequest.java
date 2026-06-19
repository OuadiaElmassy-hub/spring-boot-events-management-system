package com.pfe.backend.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UpdateSettingRequest{
    @NotBlank
    private String section;   // "notifications" ou "privacy"
    @NotBlank
    private String key;       // ex: "bookingConfirmed"
    private boolean value;

    public boolean getValue() {
        return this.value;
    }
}