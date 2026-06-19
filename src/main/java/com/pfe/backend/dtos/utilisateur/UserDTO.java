package com.pfe.backend.dtos.utilisateur;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class UserDTO {

        private Long id;
        private String email;
        private String username;
        private String nom;
        private List<String> roles;

}
