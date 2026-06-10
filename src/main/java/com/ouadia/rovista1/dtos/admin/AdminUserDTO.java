package com.ouadia.rovista1.dtos.admin;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class AdminUserDTO{
    private Long id;
    private String nom;
    private String email;
    private List<String> roles;
    private String status;
    private String createdAt;
    private String avatar;
}