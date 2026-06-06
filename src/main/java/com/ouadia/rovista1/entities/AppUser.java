package com.ouadia.rovista1.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class AppUser {
    @Id
    private String id;
    @Column(unique = true )
    private String username;
    @Column(nullable = false)
    private String password;
    @ManyToOne
    private Role role;
}
