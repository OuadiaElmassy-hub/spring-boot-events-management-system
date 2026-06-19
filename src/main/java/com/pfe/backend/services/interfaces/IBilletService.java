package com.pfe.backend.services.interfaces;


import com.pfe.backend.dtos.billet.BilletRequestDto;
import com.pfe.backend.dtos.billet.BilletResponseDto;
import com.pfe.backend.exceptions.BilletNotFoundException;
import com.pfe.backend.exceptions.ReservationNotFoundException;

import java.util.List;
import java.util.Map;

public interface IBilletService {
    public BilletResponseDto addBillet(BilletRequestDto billetDto) throws ReservationNotFoundException;
    public BilletResponseDto editBillet(BilletRequestDto billetDto ,Long idRech) throws ReservationNotFoundException;
    public BilletResponseDto editBilletMap(Long idRech , Map<String,Object> map);
    public BilletResponseDto getBilletById(Long id)throws BilletNotFoundException;
    public List<BilletResponseDto> getAllBillets();
    public void deleteBilletById(Long id);
    public void deleteAllByIds(Long ... ids);
}
