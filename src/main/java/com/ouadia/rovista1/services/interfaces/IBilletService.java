package com.ouadia.rovista1.services.interfaces;


import com.ouadia.rovista1.dtos.billet.BilletResponseDto;
import com.ouadia.rovista1.entities.Billet;
import com.ouadia.rovista1.exceptions.BilletNotFoundException;

import java.util.List;

public interface IBilletService {
    public Billet addBillet(Billet billet);
    public Billet editBillet(Billet billet);
    public BilletResponseDto getBilletById(Long id) throws BilletNotFoundException;
    public List<Billet> getAllBillets();
    public void deleteBilletById(Long id);
}
