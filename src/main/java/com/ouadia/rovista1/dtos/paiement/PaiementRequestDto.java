package com.ouadia.rovista1.dtos.paiement;
import com.ouadia.rovista1.entities.enums.MethodePaiement;
import com.ouadia.rovista1.entities.enums.StatutPaiement;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
@Data
@AllArgsConstructor
@Builder
public class PaiementRequestDto {

    private BigDecimal montant;
    private LocalDateTime datePaiement;
    private StatutPaiement statut;
    private MethodePaiement methodePaiement;
    private Long reservationId;
}
