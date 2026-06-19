package com.pfe.backend.services.admin;

import com.pfe.backend.dtos.admin.AdminEventDTO;
import com.pfe.backend.dtos.admin.PatchEventStatusRequest;
import com.pfe.backend.entities.Evenement;
import com.pfe.backend.entities.Notification;
import com.pfe.backend.entities.enums.StatutEvenement;
import com.pfe.backend.entities.enums.TypeMessage;
import com.pfe.backend.repositories.EventRepository;
import com.pfe.backend.repositories.NotificationRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class AdminEventService {

    private final EventRepository        eventRepo;
    private final NotificationRepository notifRepo;

    // Recherche avec filtres combinés
    public Page<AdminEventDTO> searchEvents(
            String search, String status, String categorie,
            String ville, int page, int size) {

        Pageable pageable = PageRequest.of( page, size);
        StatutEvenement statusEnum = null;
        if (status != null && !status.isBlank()) {
            statusEnum = mapStatus(status);
        }

        return eventRepo.searchAdmin(
            nullIfBlank(search),
            statusEnum,
            nullIfBlank(categorie),
            nullIfBlank(ville),
            pageable
        ).map(this::toDTO);
    }

    // Récents (pour le widget dashboard)
    public Page<AdminEventDTO> getRecent(Pageable pageable) {
        return eventRepo.findByOrderByDateCreationDesc(pageable)
                        .map(this::toDTO);
    }

    @Transactional
    public void patchStatus(Long eventId, PatchEventStatusRequest req) {
        Evenement e = eventRepo.findById(eventId)
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Événement introuvable"));

        StatutEvenement newStatus = mapStatus(req.getStatus());
        e.setStatutEvenement(newStatus);

        // Stocker le motif sur l'entité Event
        if (req.getMotif() != null) e.setMotif(req.getMotif());

        eventRepo.save(e);

        // Créer une notification pour l'organisateur
        Notification notif = new Notification();
        notif.setDestinataire(e.getOrganisateur());
        notif.setMessage(buildNotifMessage(e.getTitre(), req.getStatus(), req.getMotif()));
        notif.setTypeMessage(TypeMessage.EVENT_STATUS_CHANGED);
        notifRepo.save(notif);
    }

    public long countPending() {
        return eventRepo.countByStatutEvenement(StatutEvenement.EN_ATTENTE);
    }

    // ── Mapping ──────────────────────────────────────────────
    private AdminEventDTO toDTO(Evenement e) {
        return new AdminEventDTO(
            e.getId(),
            e.getTitre(),
            e.getOrganisateur() != null ? e.getOrganisateur().getNom() : "—",
            e.getCategorie() != null ? e.getCategorie().getNom() : "—",
            e.getDateDebut() != null ? e.getDateDebut().toString() : null,
            e.getLieuSpecifique(),
            e.getVille(),
            e.getCapacite(),
            e.getPrix(),
            formatStatus(e.getStatutEvenement()),
            e.getMotif(),
            e.getDateCreation() != null ? e.getDateCreation().toString() : null
        );
    }

    private StatutEvenement mapStatus(String s) {
        return switch (s) {
            case "Approuvé"   -> StatutEvenement.APPROUVE;
            case "Rejeté"     -> StatutEvenement.REJETE;
            case "En attente" -> StatutEvenement.EN_ATTENTE;
            default -> throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST, "Statut inconnu : " + s);
        };
    }

    private String formatStatus(StatutEvenement s) {
        return switch (s) {
            case APPROUVE   -> "Approuvé";
            case EN_ATTENTE -> "En attente";
            case BROUILLON -> "Brouillon";
            case REJETE     -> "Rejeté";
        };
    }

    private String buildNotifMessage(String titre, String status, String motif) {
        String base = "Votre événement \"" + titre + "\" a été " + status.toLowerCase();
        return motif != null && !motif.isBlank() ? base + " : " + motif : base + ".";
    }

    private String nullIfBlank(String s) {
        return (s == null || s.isBlank()) ? null : s;
    }
}