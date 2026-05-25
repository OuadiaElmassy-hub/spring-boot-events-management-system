package com.ouadia.rovista1.services.interfaces;


import com.ouadia.rovista1.dtos.BilletDto;
import com.ouadia.rovista1.exceptions.BilletNotFoundException;

import java.util.List;
import java.util.Map;

public interface IBilletService {
    public BilletDto addBillet(BilletDto billetDto);
    public BilletDto editBillet(BilletDto billetDto ,Long idRech);
    public BilletDto editBilletMap(Long idRech , Map<String,Object> map);
    public BilletDto getBilletById(Long id)throws BilletNotFoundException;
    public List<BilletDto> getAllBillets();
    public void deleteBilletById(Long id);
    public void deleteAllByIds(Long ... ids);
}
