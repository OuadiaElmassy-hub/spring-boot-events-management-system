package com.ouadia.rovista1.services.implementations;

import com.ouadia.rovista1.dtos.billet.BilletRequestDto;
import com.ouadia.rovista1.dtos.billet.BilletResponseDto;
import com.ouadia.rovista1.entities.*;
import com.ouadia.rovista1.entities.enums.TypeBillet;
import com.ouadia.rovista1.exceptions.BilletNotFoundException;
import com.ouadia.rovista1.exceptions.ReservationNotFoundException;
import com.ouadia.rovista1.repositories.BilletRepository;
import com.ouadia.rovista1.services.interfaces.IBilletService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
@Transactional
@AllArgsConstructor
public class BilletServiceImpl implements IBilletService {

    private BilletRepository repository;
    private com.ouadia.rovista1.mappers.BilletMapper mapper;


    @Override
    public BilletResponseDto addBillet(BilletRequestDto billetDto ) throws ReservationNotFoundException {
        Billet billet = mapper.mappingBilletDtoRequestToBillet(billetDto);
            return mapper.mappingBilletToBilletDtoResponse (repository.save(billet));
    }

    @Override
    public BilletResponseDto editBillet(BilletRequestDto billetDto, Long idRech) throws ReservationNotFoundException {
        Billet billet = mapper.mappingBilletDtoRequestToBillet(billetDto);
        if (billet == null) return null;
        else {
            Billet billet1 = repository.findById(idRech).get();
            if (billet1 == null) {
                return null;
            }
            billet1.setCode(billet.getCode());
            billet1.setQrCode(billet.getQrCode());
            billet1.setDateBillet(billet.getDateBillet());
            billet1.setType(billet.getType());
            billet1.setReservation(billet.getReservation());
            return mapper.mappingBilletToBilletDtoResponse (repository.save(billet1));
        }
    }


    @Override
    public BilletResponseDto editBilletMap(Long idRech, Map<String, Object> map) {
        if (map==null){return null;}
        Billet billet1 = repository.findById(idRech).get();
        if (billet1 == null) {
            return null;
        }
        if (map.containsKey("code")) {
            billet1.setCode((String) map.get("code"));
        }
        if (map.containsKey("qrCode")) {
            billet1.setQrCode((String) map.get("qrCode"));
        }
        if (map.containsKey("deteBillet")) {
            billet1.setDateBillet((LocalDateTime) map.get("deteBillet"));
        }

        if (map.containsKey("type")) {
            billet1.setType(TypeBillet.valueOf (map.get("type").toString()));
        }
        if (map.containsKey("reservation")) {
            billet1.setReservation((Reservation) map.get("reservation"));
        }

        return mapper.mappingBilletToBilletDtoResponse (repository.save(billet1));
    }



    @Override
    public BilletResponseDto getBilletById(Long id) throws BilletNotFoundException {
            Billet billet=repository.findById(id)
                    .orElseThrow(()->new BilletNotFoundException("billet not found"));
        return mapper.mappingBilletToBilletDtoResponse(billet);
    }




    @Override
    public List<BilletResponseDto> getAllBillets() {
        return repository.findAll().stream().map(billet-> mapper.mappingBilletToBilletDtoResponse(billet)).toList();
    }

    @Override
    public void deleteBilletById(Long id) {
        repository.deleteById(id);
    }

    @Override
    public void deleteAllByIds(Long... ids) {
        for (Long id :ids){
            deleteBilletById(id);
        }
    }
}
