package com.ouadia.rovista1.services;


import com.ouadia.rovista1.entities.Billet;

import java.util.List;

public interface IBilletService {
    public Billet addBillet(Billet billet);
    public Billet editBillet(Billet billet);
    public Billet getBilletById(Long id);
    public List<Billet> getAllBillets();
    public void deleteBilletById(Long id);
}
