package com.pfe.backend.dtos;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ProfileDTO{
    private String nom;
    private String email;
    private String telephone;
    private String ville;
    private String avatar;
    private String createdAt;
}