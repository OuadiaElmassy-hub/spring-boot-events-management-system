package com.ouadia.rovista1.controllers;

import com.ouadia.rovista1.dtos.billet.BilletRequestDto;
import com.ouadia.rovista1.dtos.billet.BilletResponseDto;
import com.ouadia.rovista1.exceptions.BilletNotFoundException;
import com.ouadia.rovista1.exceptions.ReservationNotFoundException;
import com.ouadia.rovista1.services.implementations.BilletServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@AllArgsConstructor
@RestController
@RequestMapping("/api/billets")
public class BilletController {
    final BilletServiceImpl billetService;


    // create billet
    @PostMapping
    public ResponseEntity<BilletResponseDto>createBillet(@RequestBody BilletRequestDto billet) throws ReservationNotFoundException {
        return new ResponseEntity<>((billetService.addBillet(billet)), HttpStatus.CREATED);
    }


    // racherche billet
    @GetMapping ("{id}")
    public ResponseEntity<BilletResponseDto>RechercheBillet(@PathVariable("id") Long id)throws BilletNotFoundException {
        return  ResponseEntity.ok(billetService.getBilletById(id));
    }

    @GetMapping
    public ResponseEntity<List<BilletResponseDto>>GetAllBillet()throws BilletNotFoundException {
        return  ResponseEntity.ok(billetService.getAllBillets());
    }
   //update billet
    @PutMapping("{id}")
    public ResponseEntity<BilletResponseDto>UpdateBillet (@PathVariable("id") Long id,
                                                          @RequestBody BilletRequestDto billet
                                                          ) throws BilletNotFoundException, ReservationNotFoundException {
       return ResponseEntity.ok(billetService.editBillet(billet,id));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> DeleteBillet (@PathVariable("id") Long id){
        billetService.deleteBilletById(id);
        return ResponseEntity.ok("billet deleted successfully ! ✅");
    }
}
