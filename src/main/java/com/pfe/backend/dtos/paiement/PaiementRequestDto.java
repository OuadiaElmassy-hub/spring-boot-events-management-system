package com.pfe.backend.dtos.paiement;
import com.pfe.backend.entities.enums.MethodePaiement;
import com.pfe.backend.entities.enums.StatutPaiement;
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
