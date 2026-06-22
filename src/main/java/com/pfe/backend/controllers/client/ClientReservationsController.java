package com.pfe.backend.controllers.client;


import com.pfe.backend.dtos.reservation.HistoriqueReservationDto;
import com.pfe.backend.security.SecurityUtils;
import com.pfe.backend.services.interfaces.IReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class ClientReservationsController {

    private final IReservationService bookingService;
    private final SecurityUtils securityUtils;

    // GET /api/client/reservations?statut=Confirmé&page=0&size=10&sort=date,desc
    @GetMapping("client/reservations")
    public ResponseEntity<Page<HistoriqueReservationDto>> getBookings(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestParam(required = false, defaultValue = "Confirmé") String statut,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

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

