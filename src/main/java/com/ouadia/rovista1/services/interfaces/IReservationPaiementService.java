package com.ouadia.rovista1.services.interfaces;

import com.ouadia.rovista1.dtos.paiement.CheckoutRequestDto;
import com.ouadia.rovista1.dtos.paiement.CheckoutResponseDto;

public interface IReservationPaiementService {
    CheckoutResponseDto checkout(CheckoutRequestDto dto);
}