package com.pfe.backend.dtos.auth;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class AuthResponse{
    String token;
    private String refreshToken;
    List<String> roles;
    String nom;
}
