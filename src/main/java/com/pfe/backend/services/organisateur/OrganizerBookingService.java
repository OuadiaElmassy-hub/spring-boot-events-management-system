package com.pfe.backend.services.organisateur;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.UnitValue;
import com.pfe.backend.dtos.organisateur.OrgBookingDTO;
import com.pfe.backend.dtos.organisateur.OrgBookingsPageDTO;
import com.pfe.backend.entities.Reservation;
import com.pfe.backend.entities.enums.StatutReservation;
import com.pfe.backend.repositories.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

//import org.apache.poi.ss.usermodel.Cell;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrganizerBookingService {

    private final ReservationRepository reservationRepo;

    public OrgBookingsPageDTO getBookings(
            Long orgId, Long eventId, String statut, int page, int size) {

        Pageable pageable = PageRequest.of(page, size);

        StatutReservation statusEnum = parseStatut(statut);

        Page<Reservation> pageR = reservationRepo.findByOrganizerWithFilters(
            orgId, eventId, statusEnum, pageable
        );

        Double totalRevenu = reservationRepo.totalRevenuByFilters(orgId, eventId, StatutReservation.CONFIRME);

        List<OrgBookingDTO> content = pageR.getContent()
            .stream().map(this::toDTO).toList();

        return new OrgBookingsPageDTO(
            content,
            pageR.getTotalPages(),
            pageR.getTotalElements(),
            pageR.getNumber(),
            totalRevenu != null ? totalRevenu : 0.0
        );
    }

    // Export PDF via iText
    public byte[] exportPdf(Long orgId, Long eventId, String statut) {
        List<Reservation> list = reservationRepo.findAllForExport(
            orgId, eventId, parseStatut(statut));

        return buildPdf(list);
    }

    // Export Excel via Apache POI
    public byte[] exportExcel(Long orgId, Long eventId, String statut)
            throws IOException {

        List<Reservation> list = reservationRepo.findAllForExport(
            orgId, eventId, parseStatut(statut));

        return buildExcel(list);
    }

    // ── Builders ─────────────────────────────────────────────
    private byte[] buildPdf(List<Reservation> reservations) {
        // Exemple avec iText 7
        // Ajoutez la dépendance : com.itextpdf:itext7-core:7.2.5
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            PdfWriter writer   = new PdfWriter(baos);
            PdfDocument pdfDoc   = new PdfDocument(writer);
            Document document = new Document(pdfDoc);

            document.add(new Paragraph("Liste des Réservations")
                .setFontSize(18).setBold());
            document.add(new Paragraph(
                "Généré le : " + LocalDate.now().format(
                    DateTimeFormatter.ofPattern("dd/MM/yyyy"))));

            // En-tête tableau
            Table table = new Table(UnitValue.createPercentArray(
                new float[]{3,3,3,2,2,2}))
                .useAllAvailableWidth();

            for (String h : List.of("Client","Email","Événement","Billets","Total","Statut")) {
                table.addHeaderCell(new Cell().add(new Paragraph(h).setBold()));
            }

            // Lignes
            for (Reservation r : reservations) {
                table.addCell(r.getClient() != null ? r.getClient().getNom() + " " + r.getClient().getPrenom() : r.getVisiteurInvite().getNom() + " " + r.getVisiteurInvite().getPrenom());
                table.addCell(r.getClient() != null ? r.getClient().getEmail() : r.getVisiteurInvite().getEmail());
                table.addCell(r.getEvenement().getTitre());
                table.addCell("1");
                table.addCell(r.getEvenement().getPrix() + " DH");
                table.addCell(r.getStatut().name());
            }

            document.add(table);
            document.close();
            return baos.toByteArray();

        } catch (Exception e) {
            throw new RuntimeException("Erreur génération PDF", e);
        }
    }

    private byte[] buildExcel(List<Reservation> reservations) throws IOException {
        // Apache POI — dépendance : org.apache.poi:poi-ooxml:5.2.3
        try (Workbook workbook = new XSSFWorkbook();
             ByteArrayOutputStream baos = new ByteArrayOutputStream()) {

            Sheet sheet = workbook.createSheet("Réservations");

            // En-tête
            Row header = sheet.createRow(0);
            String[] cols = {"ID","Client", "Anonym","Email","Événement","Date","Total","Statut","Paiement"};
            for (int i = 0; i < cols.length; i++) {
                org.apache.poi.ss.usermodel.Cell cell = header.createCell(i);
                cell.setCellValue(cols[i]);
                CellStyle style = workbook.createCellStyle();
                Font font = workbook.createFont();
                font.setBold(true);
                style.setFont(font);
                cell.setCellStyle(style);
            }

            // Données
            int rowNum = 1;
            for (Reservation r : reservations) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(r.getId());
                if (r.getClient() != null){
                    row.createCell(1).setCellValue(
                            r.getClient().getNom() + " " + r.getClient().getPrenom());
                    row.createCell(2).setCellValue(r.getClient().getEmail());
                } else {
                    row.createCell(1).setCellValue(
                            r.getVisiteurInvite().getNom() + " " + r.getVisiteurInvite().getPrenom());
                    row.createCell(3).setCellValue(r.getVisiteurInvite().getEmail());
                }

                row.createCell(4).setCellValue(r.getEvenement().getTitre());
                row.createCell(5).setCellValue(
                    r.getDateReservation() != null
                        ? r.getDateReservation().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
                        : "");
                row.createCell(6).setCellValue(
                    r.getEvenement().getPrix() != 0 ? r.getEvenement().getPrix() : 0);
                row.createCell(7).setCellValue(r.getStatut().name());
                row.createCell(8).setCellValue(r.getPaiement().getStatut().name());
            }

            for (int i = 0; i < cols.length; i++) sheet.autoSizeColumn(i);
            workbook.write(baos);
            return baos.toByteArray();
        }
    }

    // il faut ajouter le cas de visiteur
    private OrgBookingDTO toDTO(Reservation r) {

        if (r.getClient() != null){
            return new OrgBookingDTO(
                    r.getId(),
                    r.getClient().getNom() + " " + r.getClient().getPrenom(),
                    r.getClient().getEmail(),
                    r.getEvenement().getTitre(),
                    r.getDateReservation() != null ? r.getDateReservation().toString() : null,
                    1, // 1 billet par réservation — adaptez si vous gérez la quantité
                    r.getEvenement().getPrix() != 0 ? r.getEvenement().getPrix() :  0.0,
                    formatStatut(r.getStatut())
            );
        }else {// (r.getVisiteurInvite() != null){
            return new OrgBookingDTO(
                    r.getId(),
                    r.getVisiteurInvite().getNom() + " " + r.getVisiteurInvite().getPrenom(),
                    r.getVisiteurInvite().getEmail(),
                    r.getEvenement().getTitre(),
                    r.getDateReservation() != null ? r.getDateReservation().toString() : null,
                    1, // 1 billet par réservation — adaptez si vous gérez la quantité
                    r.getEvenement().getPrix() != 0 ? r.getEvenement().getPrix() :  0.0,
                    formatStatut(r.getStatut())
            );
        }
    }

    private StatutReservation parseStatut(String s) {
        if (s == null || s.isBlank() || s.equals("Tous")) return null;
        return switch (s) {
            case "Confirmé"   -> StatutReservation.CONFIRME;
            case "En attente" -> StatutReservation.EN_ATTENTE;
            case "Annulé"     -> StatutReservation.ANNULEE;
            default           -> null;
        };
    }

    private String formatStatut(StatutReservation s) {
        return switch (s) {
            case CONFIRME   -> "Confirmé";
            case EN_ATTENTE -> "En attente";
            case ANNULEE     -> "Annulé";
        };
    }
}