package com.ouadia.rovista1.dtos;

import lombok.Builder;

@Builder
public class ProfileDTO{
    private String nom;
    private String email;
    private String telephone;
    private String ville;
    private String avatar;
    private String createdAt;
}