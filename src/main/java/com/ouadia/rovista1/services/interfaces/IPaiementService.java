package com.ouadia.rovista1.services.interfaces;

import com.ouadia.rovista1.dtos.PaiementDto;
import com.ouadia.rovista1.entities.Paiement;
import com.ouadia.rovista1.exceptions.PaiementNotFoundException;

import java.util.List;
import java.util.Map;

public interface IPaiementService {

    public PaiementDto addPaiement(PaiementDto paiementDto);
    public PaiementDto editPaiement(PaiementDto paiementDto ,  Integer id);
    public PaiementDto editPaiementMap(  Integer id , Map<String,Object> map);
    public PaiementDto getPaiementById(  Integer id )throws PaiementNotFoundException;
    public List<PaiementDto> getAllPaiements();
    public void deletePaiementById( Integer id);
    public void deleteAllByIds(Integer ... ids);

}
