package com.ouadia.rovista1.services.implementations;

import com.ouadia.rovista1.dtos.billet.BilletResponseDto;
import com.ouadia.rovista1.dtos.paiement.CheckoutRequestDto;
import com.ouadia.rovista1.dtos.paiement.CheckoutResponseDto;
import com.ouadia.rovista1.entities.*;
import com.ouadia.rovista1.entities.enums.*;
import com.ouadia.rovista1.repositories.*;
import com.ouadia.rovista1.services.interfaces.IReservationPaiementService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class ReservationPaiementServiceImpl implements IReservationPaiementService {

    private final EventRepository evenementRepository;
    private final VisiteurRepository visiteurInviteRepository;
    private final ClientRepository clientRepository;
    private final ReservationRepository reservationRepository;
    private final PaiementRepository paiementRepository;
    private final BilletRepository billetRepository;

    @Override
    public CheckoutResponseDto checkout(CheckoutRequestDto dto) {

        // ── 1. Récupère l'événement ──
        Evenement evenement = evenementRepository.findById(dto.getEvenementId())
                .orElseThrow(() -> new RuntimeException("Événement introuvable"));

        // ── 2. Vérifie les places disponibles ──
        int nbPlaces = dto.getNombrePlaces();
        TypeBillet type = dto.getTypeBillet();

        if (type == TypeBillet.VIP) {
            if (evenement.getPlacesVIPRestantes() < nbPlaces)
                throw new RuntimeException("Pas assez de places VIP disponibles");
        } else {
            int normalesRestantes = evenement.getPlacesRestants() - evenement.getPlacesVIPRestantes();
            if (normalesRestantes < nbPlaces)
                throw new RuntimeException("Pas assez de places normales disponibles");
        }

        // ── 3. Calcul du montant ──
        double prixUnitaire = type == TypeBillet.VIP ? evenement.getPrixVIP() : evenement.getPrix();
        BigDecimal montant = BigDecimal.valueOf(prixUnitaire * nbPlaces);

        // ── 4. Visiteur ou Client ──
        VisiteurInvite visiteur = null;
        Client client = null;

        if (dto.getClientId() != null) {
            client = clientRepository.findById(dto.getClientId())
                    .orElseThrow(() -> new RuntimeException("Client introuvable"));
        } else {
            visiteur = VisiteurInvite.builder()
                    .nom(dto.getVisiteur().getNom())
                    .prenom(dto.getVisiteur().getPrenom())
                    .email(dto.getVisiteur().getEmail())
                    .phone(dto.getVisiteur().getPhone())
                    .adresse("Non renseigné")
                    .build();
            visiteur = visiteurInviteRepository.save(visiteur);
        }

        // ── 5. Crée la Reservation ──
        Reservation reservation = Reservation.builder()
                .evenement(evenement)
                .client(client)
                .visiteurInvite(visiteur)
                .nombrePlaces(nbPlaces)
                .montant(montant)
                .statut(StatutReservation.CONFIRME)
                .build();
        reservation = reservationRepository.save(reservation);

        // ── 6. Crée le Paiement ──
        Paiement paiement = Paiement.builder()
                .reservation(reservation)
                .montant(montant)
                .datePaiement(LocalDateTime.now())
                .methodePaiement(dto.getMethodePaiement())
                .statut(StatutPaiement.VALIDE)
                .build();
        paiementRepository.save(paiement);

        // ── 7. Crée les Billets (1 par place) ──
        List<Billet> billets = new ArrayList<>();
        for (int i = 0; i < nbPlaces; i++) {
            String code = "BIL-" + evenement.getId() + "-"
                    + System.currentTimeMillis() + "-"
                    + UUID.randomUUID().toString().substring(0, 4).toUpperCase();

            Billet billet = Billet.builder()
                    .code(code)
                    .qrCode(code) // QR code = le code pour l'instant
                    .dateBillet(LocalDateTime.now())
                    .type(type)
                    .reservation(reservation)
                    .build();
            billets.add(billetRepository.save(billet));
        }

        // ── 8. Met à jour les places restantes ──
        if (type == TypeBillet.VIP) {
            evenement.setPlacesVIPRestantes(evenement.getPlacesVIPRestantes() - nbPlaces);
        }
        evenement.setPlacesRestants(evenement.getPlacesRestants() - nbPlaces);
        evenementRepository.save(evenement);

        // ── 9. Retourne la réponse ──
        List<BilletResponseDto> billetsDto = billets.stream()
                .map(b -> BilletResponseDto.builder()
                        .id(b.getId())
                        .code(b.getCode())
                        .qrCode(b.getQrCode())
                        .dateBillet(b.getDateBillet())
                        .type(b.getType())
                        .build())
                .toList();

        return CheckoutResponseDto.builder()
                .reservationId(reservation.getId())
                .montant(montant)
                .statut("CONFIRME")
                .billets(billetsDto)
                .build();
    }
}