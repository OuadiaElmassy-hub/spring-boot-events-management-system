package com.ouadia.rovista1.dtos.paiement;

import com.ouadia.rovista1.dtos.billet.BilletResponseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CheckoutResponseDto {
    private Long reservationId;
    private BigDecimal montant;
    private String statut;
    private List<BilletResponseDto> billets;
}