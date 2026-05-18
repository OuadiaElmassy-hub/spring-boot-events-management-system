package com.ouadia.rovista1.services;

import com.ouadia.rovista1.entities.Paiement;

import java.util.List;

public interface IPaiementService {

    public Paiement addPaiement(Paiement paiement);
    public Paiement editPaiement(Paiement paiement);
    public Paiement getPaiementById(Long id);
    public List<Paiement> getAllPaiements();
    public void deletePaiementById(Long id);
}
