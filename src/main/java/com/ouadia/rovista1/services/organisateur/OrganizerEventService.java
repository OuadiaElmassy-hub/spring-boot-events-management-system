package com.ouadia.rovista1.services.organisateur;

import com.ouadia.rovista1.dtos.organisateur.CreateUpdateEventRequest;
import com.ouadia.rovista1.dtos.organisateur.OrgEventDTO;
import com.ouadia.rovista1.dtos.organisateur.PatchEventStatusRequest;
import com.ouadia.rovista1.entities.Evenement;
import com.ouadia.rovista1.entities.Notification;
import com.ouadia.rovista1.entities.Organisateur;
import com.ouadia.rovista1.entities.enums.StatutEvenement;
import com.ouadia.rovista1.entities.enums.StatutPaiement;
import com.ouadia.rovista1.entities.enums.StatutReservation;
import com.ouadia.rovista1.entities.enums.TypeMessage;
import com.ouadia.rovista1.exceptions.CategorieNotFoundException;
import com.ouadia.rovista1.repositories.CategorieRepository;
import com.ouadia.rovista1.repositories.EventRepository;
import com.ouadia.rovista1.repositories.NotificationRepository;
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
public class OrganizerEventService {

    /**/

    private final EventRepository    eventRepo;
    private final CategorieRepository catRepo;
    private final NotificationRepository notifRepo;

    public Page<OrgEventDTO> getEvents(
            Long orgId, String search, String status, int page, int size) {

        Pageable pageable = PageRequest.of(page, size);
        StatutEvenement statusEnum = parseStatus(status);
        return eventRepo.findByOrganizerWithFilters(
            orgId, nullIfBlank(search), statusEnum, pageable
        ).map(this::toDTO);
    }

    @Transactional
    public OrgEventDTO createEvent(Long orgId, CreateUpdateEventRequest req,
                                   Organisateur organizer) throws CategorieNotFoundException {
        Evenement e = new Evenement();
        fillEvent(e, req, organizer);
        // Nouvel événement → EN_ATTENTE de validation admin
        e.setStatutEvenement(StatutEvenement.EN_ATTENTE);
        Evenement saved = eventRepo.save(e);

        // Notifier les admins
        Notification notif = new Notification();
        notif.setDestinataire(null); // notification admin globale
        notif.setMessage("Nouvel événement soumis par "
            + organizer.getNom() + " : \"" + saved.getTitre() + "\"");
        notif.setTypeMessage(TypeMessage.EVENT_SUBMITTED);
        notifRepo.save(notif);

        return toDTO(saved);
    }

    @Transactional
    public OrgEventDTO updateEvent(Long orgId, Long eventId,
                                   CreateUpdateEventRequest req) throws CategorieNotFoundException {
        Evenement e = findOwnEvent(orgId, eventId);
        fillEvent(e, req, e.getOrganisateur());
        return toDTO(eventRepo.save(e));
    }

    @Transactional
    public void deleteEvent(Long orgId, Long eventId) {
        Evenement e = findOwnEvent(orgId, eventId);
        if (e.getStatutEvenement() == StatutEvenement.APPROUVE) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "Impossible de supprimer un événement approuvé. Contactez l'administration."
            );
        }
        eventRepo.delete(e);
    }

    @Transactional
    public void patchStatus(Long orgId, Long eventId, PatchEventStatusRequest req) {
        Evenement e = findOwnEvent(orgId, eventId);

        // L'organisateur ne peut basculer qu'entre Brouillon et soumis (EN_ATTENTE)
        // Il ne peut pas s'auto-approuver
        StatutEvenement newStatus = switch (req.getStatus()) {
            case "Publié"    -> StatutEvenement.EN_ATTENTE; // soumis à validation
            case "Brouillon" -> StatutEvenement.BROUILLON;
            default -> throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST, "Statut invalide");
        };

        // Seul un événement en brouillon peut être soumis
        if (newStatus == StatutEvenement.EN_ATTENTE
                && e.getStatutEvenement() != StatutEvenement.BROUILLON) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "Seul un brouillon peut être soumis à validation.");
        }

        e.setStatutEvenement(newStatus);
        eventRepo.save(e);
    }

    // ── Helpers ──────────────────────────────────────────────
    private Evenement findOwnEvent(Long orgId, Long eventId) {
//        Evenement e = eventRepo.findById(eventId)
//                .orElseThrow(() -> new ResponseStatusException(
//                HttpStatus.NOT_FOUND, "Événement introuvable"));
//
//        if (!e.getOrganisateur().getId().equals(orgId)) {
//            throw new ResponseStatusException(
//                HttpStatus.FORBIDDEN, "Cet événement ne vous appartient pas");
//        }
        return eventRepo.findByIdAndOrganisateurId(eventId, orgId)
                .orElseThrow(() -> new ResponseStatusException(
                    HttpStatus.FORBIDDEN, "Cet événement ne vous appartient pas"));
    }

    private void fillEvent(Evenement e, CreateUpdateEventRequest req, Organisateur organizer) throws CategorieNotFoundException {
        e.setTitre(req.getTitre());
        e.setDescription(req.getDescription());
        e.setDateDebut(req.getDate());
        e.setVille(req.getLieu());
        e.setPrix(req.getPrix());
        e.setCapacite(req.getCapacite());
        e.setOrganisateur(organizer);

        if (req.getCategorie() != null) {
            e.setCategorie(catRepo.findByNom(req.getCategorie())
                    .orElseThrow(() -> new CategorieNotFoundException("Categorie Not Found With nom : "+ req.getCategorie())));
        }
    }

    private OrgEventDTO toDTO(Evenement e) {
        long participants = e.getReservations() != null
            ? e.getReservations().stream()
                .filter(r -> r.getStatut() == StatutReservation.CONFIRME)
                .count()
            : 0;

        double revenus = e.getReservations() != null
            ? e.getReservations().stream()
                .filter(r -> r.getPaiement().getStatut() == StatutPaiement.VALIDE)
                .mapToDouble(r -> e.getPrix() != 0 ? e.getPrix() : 0)
                .sum()
            : 0.0;

        return new OrgEventDTO(
            e.getId(),
            e.getTitre(),
            e.getDescription(),
            e.getDateDebut() != null ? e.getDateDebut().toString() : null,
            e.getVille(),
            e.getPrix(),
            e.getCapacite(),
            (int) participants,
            revenus,
            formatStatus(e.getStatutEvenement()),
            e.getCategorie() != null ? e.getCategorie().getNom() : null,
            e.getDateCreation() != null ? e.getDateCreation().toString() : null
        );
    }

    private StatutEvenement parseStatus(String s) {
        if (s == null || s.isBlank() || s.equals("Tous")) return null;
        return switch (s) {
            case "Publié"     -> StatutEvenement.APPROUVE;
            case "Brouillon"  -> StatutEvenement.BROUILLON;
            case "En attente" -> StatutEvenement.EN_ATTENTE;
            default -> null;
        };
    }

    private String formatStatus(StatutEvenement s) {
        if (s == null) return null;
        return switch (s) {
            case APPROUVE   -> "Publié";
            case BROUILLON  -> "Brouillon";
            case EN_ATTENTE -> "En attente";
            case REJETE     -> "Rejeté";
        };
    }

    private String nullIfBlank(String s) {
        return (s == null || s.isBlank()) ? null : s;
    }
}