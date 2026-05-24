package com.ouadia.rovista1.services.implementations;

import com.ouadia.rovista1.Mapper.AvisMapper;
import com.ouadia.rovista1.Mapper.BilletMapper;
import com.ouadia.rovista1.dtos.BilletDto;
import com.ouadia.rovista1.entities.*;
import com.ouadia.rovista1.entities.enums.TypeBillet;
import com.ouadia.rovista1.exceptions.AvisNotFoundException;
import com.ouadia.rovista1.exceptions.BilletNotFoundException;
import com.ouadia.rovista1.repositories.BilletRepository;
import com.ouadia.rovista1.services.interfaces.IBilletService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
@Transactional
@AllArgsConstructor
public class BilletServiceImpl implements IBilletService {

    private BilletRepository repository;


    @Override
    public BilletDto addBillet(BilletDto billetDto ) {
        Billet billet = BilletMapper.mapToBillet(billetDto);
        if (repository.findById(billet.getId()).isPresent()) {
            throw new RuntimeException(" billet exsist ");
        } else
            return BilletMapper.mapToBilletDto (repository.save(billet));
    }

    @Override
    public BilletDto editBillet(BilletDto billetDto, Long idRech) {
        Billet billet = BilletMapper.mapToBillet(billetDto);
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
            return BilletMapper.mapToBilletDto (repository.save(billet1));
        }
    }


    @Override
    public BilletDto editBilletMap(Long idRech, Map<String, Object> map) {
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

        return BilletMapper.mapToBilletDto (repository.save(billet1));
    }



    @Override
    public BilletDto getBilletById(Long id) throws BilletNotFoundException {
            Billet billet=repository.findById(id)
                    .orElseThrow(()->new BilletNotFoundException("billet not found"));
        return BilletMapper.mapToBilletDto(billet);
    }




    @Override
    public List<BilletDto> getAllBillets() {
        return repository.findAll().stream().map(billet-> BilletMapper.mapToBilletDto(billet)).toList();
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
