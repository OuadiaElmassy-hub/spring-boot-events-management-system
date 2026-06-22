package com.pfe.backend.services.interfaces;

import com.pfe.backend.dtos.paiement.CheckoutRequestDto;
import com.pfe.backend.dtos.paiement.CheckoutResponseDto;

public interface IReservationPaiementService {
    CheckoutResponseDto checkout(CheckoutRequestDto dto);
}