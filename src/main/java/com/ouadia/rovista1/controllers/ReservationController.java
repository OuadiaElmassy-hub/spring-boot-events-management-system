package com.ouadia.rovista1.controllers;


import com.ouadia.rovista1.dtos.reservation.HistoriqueReservationDto;
import com.ouadia.rovista1.dtos.reservation.ReservationRequestDto;
import com.ouadia.rovista1.dtos.reservation.ReservationResponseDto;
import com.ouadia.rovista1.entities.enums.StatutReservation;
import com.ouadia.rovista1.exceptions.ReservationNotFoundException;

import com.ouadia.rovista1.security.SecurityUtils;
import com.ouadia.rovista1.services.implementations.ReservationServiceImpl;
import com.ouadia.rovista1.services.interfaces.IClientService;
import com.ouadia.rovista1.services.interfaces.IReservationService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
@AllArgsConstructor
@RestController
@RequestMapping("/api")
public class ReservationController {
    private IReservationService reservationService;

    @PostMapping
    public ResponseEntity<ReservationResponseDto> createReservation(@RequestBody ReservationRequestDto billet) {
        return new ResponseEntity<>((reservationService.addReservation(billet)), HttpStatus.CREATED);
    }


    // racherche billet
    @GetMapping("/reservations/{id}")
    public ResponseEntity<ReservationResponseDto>RechercheReservation(@PathVariable("id") Long id)throws ReservationNotFoundException {
        return  ResponseEntity.ok(reservationService.getReservationById(id));
    }

    @GetMapping
    public ResponseEntity<List<ReservationResponseDto>>GetAllReservation() {
        return  ResponseEntity.ok(reservationService.getAllReservations());
    }
    //update billet
    @PutMapping("/reservations/{id}")
    public ResponseEntity<ReservationResponseDto>UpdateReservation (@PathVariable("id") Long id,
                                                          @RequestBody ReservationRequestDto billet
    ) throws ReservationNotFoundException, ReservationNotFoundException {
        return ResponseEntity.ok(reservationService.editReservation(billet,id));
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<String> DeleteReservation (@PathVariable("id") Long id){
        reservationService.deleteReservationById(id);
        return ResponseEntity.ok("reservation  deleted successfully ! ✅");
    }

}

