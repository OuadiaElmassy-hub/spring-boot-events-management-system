package com.pfe.backend.controllers;



import com.pfe.backend.dtos.paiement.PaiementRequestDto;
import com.pfe.backend.dtos.paiement.PaiementResponseDto;
import com.pfe.backend.exceptions.PaiementNotFoundException;
import com.pfe.backend.exceptions.ReservationNotFoundException;
import com.pfe.backend.services.implementations.PaiementServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/paiements")
public class PaiementController {
    private final PaiementServiceImpl reservationService;

    @PostMapping
    public ResponseEntity<PaiementResponseDto> createPaiement(@RequestBody PaiementRequestDto paiement) throws PaiementNotFoundException, ReservationNotFoundException {

        return new ResponseEntity<>((reservationService.addPaiement(paiement)), HttpStatus.CREATED);
    }


    // racherche paiement
    @GetMapping("{id}")
    public ResponseEntity<PaiementResponseDto>RecherchePaiement(@PathVariable("id") Integer id)throws PaiementNotFoundException {
        return  ResponseEntity.ok(reservationService.getPaiementById(id));
    }

    @GetMapping
    public ResponseEntity<List<PaiementResponseDto>>GetAllPaiement()throws PaiementNotFoundException {
        return  ResponseEntity.ok(reservationService.getAllPaiements());
    }
    //update paiement
    @PutMapping("{id}")
    public ResponseEntity<PaiementResponseDto>UpdatePaiement (@PathVariable("id") Integer id,
                                                                    @RequestBody PaiementRequestDto paiement
    ) throws PaiementNotFoundException, PaiementNotFoundException, ReservationNotFoundException {
        return ResponseEntity.ok(reservationService.editPaiement(paiement,id));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> DeletePaiement (@PathVariable("id") Integer id){
        reservationService.deletePaiementById(id);
        return ResponseEntity.ok("paiement deleted successfully ! ✅");
    }
}
