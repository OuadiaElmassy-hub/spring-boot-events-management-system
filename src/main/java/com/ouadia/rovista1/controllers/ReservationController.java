package com.ouadia.rovista1.controllers;


import com.ouadia.rovista1.dtos.reservation.ReservationRequestDto;
import com.ouadia.rovista1.dtos.reservation.ReservationResponseDto;
import com.ouadia.rovista1.exceptions.ReservationNotFoundException;

import com.ouadia.rovista1.services.implementations.ReservationServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@AllArgsConstructor
@RestController
@RequestMapping("/api/reservations")
public class ReservationController {
  private ReservationServiceImpl reservationService;
    @PostMapping
    public ResponseEntity<ReservationResponseDto> createReservation(@RequestBody ReservationRequestDto billet) throws ReservationNotFoundException {
        return new ResponseEntity<>((reservationService.addReservation(billet)), HttpStatus.CREATED);
    }


    // racherche billet
    @GetMapping("{id}")
    public ResponseEntity<ReservationResponseDto>RechercheReservation(@PathVariable("id") Long id)throws ReservationNotFoundException {
        return  ResponseEntity.ok(reservationService.getReservationById(id));
    }

    @GetMapping
    public ResponseEntity<List<ReservationResponseDto>>GetAllReservation()throws ReservationNotFoundException {
        return  ResponseEntity.ok(reservationService.getAllReservations());
    }
    //update billet
    @PutMapping("{id}")
    public ResponseEntity<ReservationResponseDto>UpdateReservation (@PathVariable("id") Long id,
                                                          @RequestBody ReservationRequestDto billet
    ) throws ReservationNotFoundException, ReservationNotFoundException {
        return ResponseEntity.ok(reservationService.editReservation(billet,id));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> DeleteReservation (@PathVariable("id") Long id){
        reservationService.deleteReservationById(id);
        return ResponseEntity.ok("reservation  deleted successfully ! ✅");
    }
}

