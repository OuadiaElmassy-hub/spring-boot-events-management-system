package com.ouadia.rovista1.dtos;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class RegisterClientRequest {
    String username;
    String email;
    String password;
    String nom;
    String phone;
    String ville;
}