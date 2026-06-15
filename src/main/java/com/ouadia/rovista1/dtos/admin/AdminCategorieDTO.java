package com.ouadia.rovista1.dtos.admin;

import lombok.*;

import java.time.LocalDateTime;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdminCategorieDTO {

    private Long    id;
    private String  nom;
    private String  description;
    private String  iconUrl;
    private String  couleur;
    private Boolean active;
    private Long    totalEvents;
    private LocalDateTime createdAt;

}

