package com.ouadia.rovista1.dtos.client;

import com.ouadia.rovista1.entities.Avis;
import com.ouadia.rovista1.entities.Favorie;
import com.ouadia.rovista1.entities.Promotion;
import com.ouadia.rovista1.entities.Reservation;
import com.ouadia.rovista1.entities.enums.StatutCompte;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data // pour les methode getter, setter, toString()  ,equals()
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClientResponseDto {

    private Long id;
    private String username;
    private String email;
    private StatutCompte statutCompte;
    private String phone;
    private String adresse;
    private String nom;
    private String prenom;
    private LocalDate dateNaissance;
    private List<Long> promotionsId;
    private List<Long> reservationsId;
    private List<Long> avisId;
    private List<Long> favoritesId;
}
