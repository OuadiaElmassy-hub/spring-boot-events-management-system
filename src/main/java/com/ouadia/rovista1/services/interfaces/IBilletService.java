package com.ouadia.rovista1.services.interfaces;


import com.ouadia.rovista1.dtos.billet.BilletRequestDto;
import com.ouadia.rovista1.dtos.billet.BilletResponseDto;
import com.ouadia.rovista1.exceptions.BilletNotFoundException;
import com.ouadia.rovista1.exceptions.ReservationNotFoundException;

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
