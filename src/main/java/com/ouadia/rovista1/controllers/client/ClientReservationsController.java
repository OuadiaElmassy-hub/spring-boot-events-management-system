package com.ouadia.rovista1.controllers.client;


import com.ouadia.rovista1.dtos.reservation.HistoriqueReservationDto;
import com.ouadia.rovista1.dtos.reservation.ReservationRequestDto;
import com.ouadia.rovista1.dtos.reservation.ReservationResponseDto;
import com.ouadia.rovista1.exceptions.ReservationNotFoundException;
import com.ouadia.rovista1.security.SecurityUtils;
import com.ouadia.rovista1.services.interfaces.IClientService;
import com.ouadia.rovista1.services.interfaces.IReservationService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@CrossOrigin("*")
@RequestMapping("/api")
public class ClientReservationsController {

    private final IReservationService bookingService;
    private SecurityUtils securityUtils;

    // GET /api/client/reservations?statut=Confirmé&page=0&size=10&sort=date,desc
    @GetMapping("client/reservations")
    public ResponseEntity<Page<HistoriqueReservationDto>> getBookings(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestParam(required = false) String statut,
            @RequestParam int page,
            @RequestParam int size) {

        Long userId = securityUtils.getCurrentUserId();

        return ResponseEntity.ok(
                bookingService.getBookings(userId, statut, page, size));
    }

    // GET /api/client/reservations/{id}/ticket  → PDF
    @GetMapping("client/reservations/{id}/ticket")
    public ResponseEntity<byte[]> downloadTicket(
            @PathVariable Long id,
            @AuthenticationPrincipal UserDetails userDetails) {

        Long userId = securityUtils.getCurrentUserId();
        byte[] pdf  = bookingService.generateTicketPdf(id, userId);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, "application/pdf")
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"billet-" + id + ".pdf\"")
                .body(pdf);
    }

}

