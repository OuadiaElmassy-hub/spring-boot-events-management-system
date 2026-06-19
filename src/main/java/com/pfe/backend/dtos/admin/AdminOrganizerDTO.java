package com.pfe.backend.dtos.admin;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AdminOrganizerDTO{
    private Long id;
    private String nom;
    private String email;
    private String ville;
    private boolean verified;
    private Long totalEvents;
    private Double totalRevenue;
}