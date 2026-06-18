package com.ouadia.rovista1.dtos.reservation;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class HistoriqueReservationDto {

    private Long    id;
    private String  titre;
    private String  date;           // ISO ou formaté
    private String  lieu;
    private Double  prix;
    private String  statut;          // "Confirmé" / "En attente" / "Annulé"
    private String  paiement;
}
