package com.pfe.backend.services.implementations;

import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.properties.TextAlignment;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.pfe.backend.entities.Billet;
import com.pfe.backend.entities.Reservation;
import com.pfe.backend.repositories.BilletRepository;
import com.pfe.backend.repositories.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PdfBilletService {

    private final ReservationRepository reservationRepository;
    private final BilletRepository billetRepository;

    public byte[] generatePdf(Long reservationId) throws IOException, WriterException {
        // Récupère la réservation
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new RuntimeException("Réservation introuvable"));

        // Récupère les billets
        List<Billet> billets = billetRepository.findByReservationId(reservationId);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfWriter writer = new PdfWriter(baos);
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf, PageSize.A4);
        document.setMargins(30, 40, 30, 40);

        PdfFont fontBold = PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD);
        PdfFont fontNormal = PdfFontFactory.createFont(StandardFonts.HELVETICA);

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

        for (int i = 0; i < billets.size(); i++) {
            Billet billet = billets.get(i);

            // ── Header ──
            Paragraph header = new Paragraph("🎟 BILLET D'ENTRÉE")
                    .setFont(fontBold)
                    .setFontSize(22)
                    .setFontColor(ColorConstants.WHITE)
                    .setBackgroundColor(ColorConstants.BLUE)
                    .setTextAlignment(TextAlignment.CENTER)
                    .setPadding(15);
            document.add(header);

            // ── Nom événement ──
            document.add(new Paragraph(reservation.getEvenement().getTitre())
                    .setFont(fontBold)
                    .setFontSize(18)
                    .setFontColor(ColorConstants.BLUE)
                    .setTextAlignment(TextAlignment.CENTER)
                    .setMarginTop(10));

            // ── Infos événement ──
            document.add(new Paragraph("📅 Date : " + reservation.getEvenement().getDateDebut().format(dtf))
                    .setFont(fontNormal).setFontSize(12).setMarginTop(5));
            document.add(new Paragraph("📍 Lieu : " + reservation.getEvenement().getLieuSpecifique()
                    + " - " + reservation.getEvenement().getVille())
                    .setFont(fontNormal).setFontSize(12));

            // ── Séparateur ──
            document.add(new LineSeparator(new com.itextpdf.kernel.pdf.canvas.draw.DashedLine())
                    .setMarginTop(10).setMarginBottom(10));

            // ── Infos propriétaire ──
            String nom = reservation.getClient() != null
                    ? reservation.getClient().getNom() + " " + reservation.getClient().getPrenom()
                    : reservation.getVisiteurInvite().getNom() + " " + reservation.getVisiteurInvite().getPrenom();

            String email = reservation.getClient() != null
                    ? reservation.getClient().getEmail()
                    : reservation.getVisiteurInvite().getEmail();

            document.add(new Paragraph("👤 Titulaire : " + nom)
                    .setFont(fontBold).setFontSize(13));
            document.add(new Paragraph("📧 Email : " + email)
                    .setFont(fontNormal).setFontSize(12));

            // ── Infos billet ──
            document.add(new LineSeparator(new com.itextpdf.kernel.pdf.canvas.draw.DashedLine())
                    .setMarginTop(10).setMarginBottom(10));

            document.add(new Paragraph("🎫 Type : " + billet.getType().name())
                    .setFont(fontBold).setFontSize(13)
                    .setFontColor(billet.getType().name().equals("VIP")
                            ? ColorConstants.ORANGE : ColorConstants.DARK_GRAY));

            document.add(new Paragraph("🔑 Code : " + billet.getCode())
                    .setFont(fontBold).setFontSize(13));

            document.add(new Paragraph("Billet " + (i + 1) + " / " + billets.size())
                    .setFont(fontNormal).setFontSize(11)
                    .setFontColor(ColorConstants.GRAY));

            // ── QR Code ──
            byte[] qrBytes = generateQrCode(billet.getCode(), 200, 200);
            Image qrImage = new Image(ImageDataFactory.create(qrBytes))
                    .setWidth(150).setHeight(150)
                    .setHorizontalAlignment(com.itextpdf.layout.properties.HorizontalAlignment.CENTER)
                    .setMarginTop(15);
            document.add(qrImage);

            document.add(new Paragraph("Scannez ce code à l'entrée")
                    .setFont(fontNormal).setFontSize(10)
                    .setFontColor(ColorConstants.GRAY)
                    .setTextAlignment(TextAlignment.CENTER));

            // ── Footer ──
            document.add(new Paragraph("Rovista Events • Ce billet est nominatif et non remboursable")
                    .setFont(fontNormal).setFontSize(9)
                    .setFontColor(ColorConstants.LIGHT_GRAY)
                    .setTextAlignment(TextAlignment.CENTER)
                    .setMarginTop(20));

            // Nouvelle page pour le prochain billet
            if (i < billets.size() - 1) {
                document.add(new AreaBreak());
            }
        }

        document.close();
        return baos.toByteArray();
    }

    // ── Génère le QR code en bytes PNG ──
    private byte[] generateQrCode(String content, int width, int height)
            throws WriterException, IOException {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(content, BarcodeFormat.QR_CODE, width, height);
        ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(bitMatrix, "PNG", pngOutputStream);
        return pngOutputStream.toByteArray();
    }
}