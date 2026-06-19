package com.pfe.backend.controllers;


import com.pfe.backend.dtos.reservation.ReservationRequestDto;
import com.pfe.backend.dtos.reservation.ReservationResponseDto;
import com.pfe.backend.exceptions.ReservationNotFoundException;

import com.pfe.backend.services.interfaces.IReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/reservations")
public class ReservationController {
    private final IReservationService reservationService;

    @PostMapping
    public ResponseEntity<ReservationResponseDto> createReservation(@RequestBody ReservationRequestDto billet) {
        return new ResponseEntity<>((reservationService.addReservation(billet)), HttpStatus.CREATED);
    }


    // racherche billet
    @GetMapping("/{id}")
    public ResponseEntity<ReservationResponseDto>RechercheReservation(@PathVariable("id") Long id)throws ReservationNotFoundException {
        return  ResponseEntity.ok(reservationService.getReservationById(id));
    }

    @GetMapping
    public ResponseEntity<List<ReservationResponseDto>>GetAllReservation() {
        return  ResponseEntity.ok(reservationService.getAllReservations());
    }
    //update billet
    @PutMapping("/{id}")
    public ResponseEntity<ReservationResponseDto>UpdateReservation (@PathVariable("id") Long id,
                                                          @RequestBody ReservationRequestDto billet
    ) throws ReservationNotFoundException, ReservationNotFoundException {
        return ResponseEntity.ok(reservationService.editReservation(billet,id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> DeleteReservation (@PathVariable("id") Long id){
        reservationService.deleteReservationById(id);
        return ResponseEntity.ok("reservation  deleted successfully ! ✅");
    }

}

