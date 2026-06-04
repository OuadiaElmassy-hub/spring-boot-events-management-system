package com.ouadia.rovista1.controllers;



import com.ouadia.rovista1.dtos.paiement.PaiementRequestDto;
import com.ouadia.rovista1.dtos.paiement.PaiementResponseDto;
import com.ouadia.rovista1.exceptions.PaiementNotFoundException;
import com.ouadia.rovista1.exceptions.ReservationNotFoundException;
import com.ouadia.rovista1.services.implementations.PaiementServiceImpl;
import com.ouadia.rovista1.services.interfaces.IPaiementService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/paiements")
public class PaiementController {
    private PaiementServiceImpl reservationService;

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
