package com.ouadia.rovista1.services.interfaces;


import com.ouadia.rovista1.entities.Avis;

import java.util.List;

public interface IAvisService {
    public Avis addAvis(Avis avis);
    public Avis editAvis(Avis avis);
    public Avis getAvisById(Long id);
    public List<Avis> getAllAviss();
    public void deleteAvisById(Long id);
}
