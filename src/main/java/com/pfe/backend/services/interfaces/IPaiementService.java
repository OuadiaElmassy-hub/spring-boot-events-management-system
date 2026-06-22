package com.pfe.backend.services.interfaces;

import com.pfe.backend.dtos.paiement.PaiementRequestDto;
import com.pfe.backend.dtos.paiement.PaiementResponseDto;
import com.pfe.backend.exceptions.PaiementNotFoundException;
import com.pfe.backend.exceptions.ReservationNotFoundException;

import java.util.List;
import java.util.Map;

public interface IPaiementService {

    public PaiementResponseDto addPaiement(PaiementRequestDto paiementDto) throws ReservationNotFoundException;
    public PaiementResponseDto editPaiement(PaiementRequestDto paiementDto ,  Integer id) throws ReservationNotFoundException;
    public PaiementResponseDto editPaiementMap(  Integer id , Map<String,Object> map);
    public PaiementResponseDto getPaiementById(  Integer id )throws PaiementNotFoundException;
    public List<PaiementResponseDto> getAllPaiements();
    public void deletePaiementById( Integer id);
    public void deleteAllByIds(Integer ... ids);

}
