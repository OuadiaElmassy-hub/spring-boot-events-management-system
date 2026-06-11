package com.ouadia.rovista1.controllers.organisateur;

import com.ouadia.rovista1.dtos.organisateur.OrgBookingsPageDTO;
import com.ouadia.rovista1.security.SecurityUtils;
import com.ouadia.rovista1.services.organisateur.OrganizerBookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/organisateur/reservations")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ORGANISATEUR')")
public class OrganizerBookingController {

    private final OrganizerBookingService bookingService;
    private final SecurityUtils           securityUtils;

    // GET /api/organisateur/reservations?eventId=&statut=&page=0&size=10
    @GetMapping
    public ResponseEntity<OrgBookingsPageDTO> getBookings(
            @RequestParam(required = false) Long   eventId,
            @RequestParam(required = false) String statut,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Long orgId = securityUtils.getCurrentUserId();
        return ResponseEntity.ok(
            bookingService.getBookings(orgId, eventId, statut, page, size));
    }

    // GET /api/organisateur/reservations/export/pdf?eventId=&statut=
    @GetMapping("/export/pdf")
    public ResponseEntity<byte[]> exportPdf(
            @RequestParam(required = false) Long   eventId,
            @RequestParam(required = false) String statut) {

        Long orgId = securityUtils.getCurrentUserId();
        byte[] pdf = bookingService.exportPdf(orgId, eventId, statut);

        return ResponseEntity.ok()
            .header(HttpHeaders.CONTENT_TYPE, "application/pdf")
            .header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"reservations.pdf\"")
            .body(pdf);
    }

    // GET /api/organisateur/reservations/export/excel?eventId=&statut=
    @GetMapping("/export/excel")
    public ResponseEntity<byte[]> exportExcel(
            @RequestParam(required = false) Long   eventId,
            @RequestParam(required = false) String statut) throws IOException {

        Long orgId = securityUtils.getCurrentUserId();
        byte[] xlsx = bookingService.exportExcel(orgId, eventId, statut);

        return ResponseEntity.ok()
            .header(HttpHeaders.CONTENT_TYPE,
                "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
            .header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"reservations.xlsx\"")
            .body(xlsx);
    }
}