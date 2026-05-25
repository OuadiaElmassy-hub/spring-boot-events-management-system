package com.ouadia.rovista1.Mapper;

import com.ouadia.rovista1.dtos.PaiementDto;
import com.ouadia.rovista1.entities.Paiement;

public class PaiementMapper {
    public static PaiementDto mapToPaiementDto(Paiement paiement){

        return new PaiementDto(
                paiement.getId(),
                paiement.getMontant(),
                paiement.getDatePaiement(),
                paiement.getStatut(),
                paiement.getMethodePaiement()
        );
    }

    public static Paiement mapToPaiement(PaiementDto dto){

        return new Paiement(
                dto.getId(),
                dto.getMontant(),
                dto.getDatePaiement(),
                dto.getStatut(),
                dto.getMethodePaiement(),
                null
        );
    }
}
