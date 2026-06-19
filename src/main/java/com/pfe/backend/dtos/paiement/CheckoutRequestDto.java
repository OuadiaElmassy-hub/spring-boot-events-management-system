package com.pfe.backend.dtos.paiement;

import com.pfe.backend.dtos.visiteur.VisiteurInviteRequestDto;
import com.pfe.backend.entities.enums.MethodePaiement;
import com.pfe.backend.entities.enums.TypeBillet;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CheckoutRequestDto {
    // Infos événement
    private Long evenementId;
    private int nombrePlaces;
    private TypeBillet typeBillet;

    // Si non connecté → infos visiteur
    private VisiteurInviteRequestDto visiteur;

    // Si connecté → id du client
    private Long clientId;

    // Infos paiement
    private MethodePaiement methodePaiement;
}