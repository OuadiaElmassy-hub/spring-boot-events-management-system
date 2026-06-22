package com.pfe.backend.controllers;

import com.pfe.backend.dtos.paiement.CheckoutRequestDto;
import com.pfe.backend.dtos.paiement.CheckoutResponseDto;
import com.pfe.backend.services.interfaces.IReservationPaiementService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.pfe.backend.services.implementations.PdfBilletService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

@RestController
@CrossOrigin
@RequestMapping("/api")
@RequiredArgsConstructor
public class ReservationPaiementController {

    private final IReservationPaiementService service;
    private final PdfBilletService pdfBilletService;

    @PostMapping("/public/checkout")
    public ResponseEntity<CheckoutResponseDto> checkout(
            @RequestBody CheckoutRequestDto dto) {
        return ResponseEntity.ok(service.checkout(dto));
    }

    @GetMapping("/public/checkout/{reservationId}/pdf")
    public ResponseEntity<byte[]> downloadPdf(
            @PathVariable Long reservationId) throws Exception {

        byte[] pdf = pdfBilletService.generatePdf(reservationId);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "billets-" + reservationId + ".pdf");

        return ResponseEntity.ok()
                .headers(headers)
                .body(pdf);
    }
}