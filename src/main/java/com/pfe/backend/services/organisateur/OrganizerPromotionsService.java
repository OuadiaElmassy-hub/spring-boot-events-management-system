package com.pfe.backend.services.organisateur;

import com.pfe.backend.dtos.organisateur.CreateUpdatePromotionRequest;
import com.pfe.backend.dtos.organisateur.OrgPromotionDTO;
import com.pfe.backend.dtos.organisateur.PatchPromotionStatusRequest;
import com.pfe.backend.entities.Evenement;
import com.pfe.backend.entities.Organisateur;
import com.pfe.backend.entities.Promotion;
import com.pfe.backend.entities.enums.StatutPromotion;
import com.pfe.backend.exceptions.CategorieNotFoundException;
import com.pfe.backend.exceptions.EventNotFoundException;
import com.pfe.backend.exceptions.PromotionNotFoundException;
import com.pfe.backend.repositories.*;
import com.pfe.backend.services.FileStorageService;
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
public class OrganizerPromotionsService {


    private final PromotionRepository promotionRepository;
    private final EventRepository eventRepo;
    private final CategorieRepository catRepo;
    private final NotificationRepository notifRepo;
    private final FileStorageService storageService;
    private final ImageRepository imageRepository;


    public Page<OrgPromotionDTO> getPromotions(
            Long orgId, int page, int size) {

        Pageable pageable = PageRequest.of(page, size);
        //StatutPromotion statusEnum = parseStatus(status);
        return promotionRepository.findByOrganisateurId(orgId, pageable)
//                .findByOrganizerWithFilters(
//            orgId, nullIfBlank(search), statusEnum, pageable
//        )
                .map(this::toDTO);
    }

    @Transactional
    public OrgPromotionDTO createPromotion(Long orgId, CreateUpdatePromotionRequest req,
                                   Organisateur organizer) throws EventNotFoundException {
        Promotion p = new Promotion();
        fillPromotion(p, req, organizer);
        p.setStatutPromotion(StatutPromotion.ACTIVE);
        p.isValide();
        //Promotion saved = promotionRepository.save(p);

        if (req.getEventId() != null) {
            Evenement e = eventRepo.findById( req.getEventId())
                    .orElseThrow(() -> new EventNotFoundException("Event Not Found With id : " + req.getEventId()));
            p.setEvenement(e);
            e.setPromotion(p);
            eventRepo.save(e);
        }

        Promotion saved = promotionRepository.save(p);

        return toDTO(saved);
    }


    @Transactional
    public OrgPromotionDTO updatePromotion(Long orgId, Long eventId,
                                   CreateUpdatePromotionRequest req) throws CategorieNotFoundException, EventNotFoundException {
        Promotion e = findOwnPromotion(orgId, eventId);
        fillPromotion(e, req, e.getOrganisateur());
        return toDTO(promotionRepository.save(e));
    }

    @Transactional
    public void deletePromotion(Long orgId, Long eventId) {
        Promotion p = findOwnPromotion(orgId, eventId);
//        if (p.getStatutPromotion() == StatutPromotion.ACTIVE) {
        if (p.getActive()) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "Impossible de supprimer une promotion Active. Contactez l'administration."
            );
        }
        promotionRepository.delete(p);
    }

    public void togglePromotion(Long id) throws PromotionNotFoundException {

        Promotion prom = promotionRepository.findById(id).orElseThrow(() -> new
                PromotionNotFoundException("Promotion not found with id : "+id));
        prom.setActive(!Boolean.TRUE.equals(prom.getActive()));
        promotionRepository.save(prom);
    }

    @Transactional
    public void patchStatus(Long orgId, Long eventId, PatchPromotionStatusRequest req) {
        Promotion p = findOwnPromotion(orgId, eventId);

        StatutPromotion newStatus = switch (req.getStatus()) {
            case "Active" -> StatutPromotion.ACTIVE;
            case "Désactivée" -> StatutPromotion.DESACTIVEE;
            case "Expirée" -> StatutPromotion.EXPIREE;
            default -> throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST, "Statut invalide");
        };

//        if (newStatus == StatutPromotion.
//                && e.getStatutPromotion() != StatutPromotion.BROUILLON) {
//            throw new ResponseStatusException(
//                HttpStatus.BAD_REQUEST,
//                "Seul un brouillon peut être soumis à validation.");
//        }

        p.setStatutPromotion(newStatus);
        promotionRepository.save(p);
    }

    // ── Helpers ──────────────────────────────────────────────
    private Promotion findOwnPromotion(Long orgId, Long promId) {
//        Promotion e = eventRepo.findById(eventId)
//                .orElseThrow(() -> new ResponseStatusException(
//                HttpStatus.NOT_FOUND, "Événement introuvable"));
//
//        if (!e.getOrganisateur().getId().equals(orgId)) {
//            throw new ResponseStatusException(
//                HttpStatus.FORBIDDEN, "Cet événement ne vous appartient pas");
//        }
        return promotionRepository.findByIdAndOrganisateurId(promId, orgId)
                .orElseThrow(() -> new ResponseStatusException(
                    HttpStatus.FORBIDDEN, "Cet promotion ne vous appartient pas"));
    }

    private void fillPromotion(Promotion p, CreateUpdatePromotionRequest req, Organisateur organizer) throws EventNotFoundException {
        p.setTitre(req.getTitre());
        p.setDateDebut(req.getDateDebut());
        p.setDateFin(req.getDateFin());
        p.setCode(req.getCode());
        p.setType(req.getType());
        p.setValeur(req.getValeur());
        p.setMaxUtilisations(req.getMaxUtilisations());
        p.setMontantMinimum(req.getMontantMinimum());

        p.setOrganisateur(organizer);
    }

    private OrgPromotionDTO toDTO(Promotion p) {

        return OrgPromotionDTO.builder()
                .id(p.getId())
                .titre(p.getTitre())
                .code(p.getCode())
                .dateDebut(p.getDateDebut())
                .dateFin(p.getDateFin())
                .type(p.getType())
                .valeur(p.getValeur())
                .eventTitre(p.getEvenement() != null ? p.getEvenement().getTitre() : null)
                .montantMinimum(p.getMontantMinimum())
                //.nbUtilisations()
                .maxUtilisations(p.getMaxUtilisations())
                .active(p.getActive())
                .valide(p.getValid())
                .build();

    }
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//    @Column(nullable = false)
//    private String titre;
//
//    @Column(nullable = false)
//    private boolean estApprove;
//
//    @ManyToOne
//    private Organisateur organisateur;
//    @ManyToMany
//    private List<Client> clients;
//    @OneToMany(mappedBy = "promotion", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<Evenement> evenements;
//
//    @Column(nullable = false, unique = true, length = 50)
//    private String code;
//
//    @Column(length = 500)
//    private String description;
//
//    /** POURCENTAGE | MONTANT_FIXE */
//    @Column(nullable = false, length = 20)
//    @Builder.Default
//    private TypePromotion type = TypePromotion.POURCENTAGE;
//
//    @Column(nullable = false, precision = 10, scale = 2)
//    private BigDecimal valeur;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "evenement_id", nullable = false)
//    private Evenement evenement;
//
//    @Column(name = "date_debut")
//    private LocalDate dateDebut;
//
//    @Column(name = "date_fin")
//    private LocalDate dateFin;
//
//    @Column(name = "max_utilisations")
//    private Integer maxUtilisations;
//
//    @Column(name = "nb_utilisations", nullable = false)
//    @Builder.Default
//    private Integer nbUtilisations = 0;
//
//    @Column(name = "montant_minimum", precision = 10, scale = 2)
//    private BigDecimal montantMinimum;
//
//    @Column(nullable = false)
//    @Builder.Default
//    private Boolean active = true;
//
//    @CreationTimestamp
//    @Column(name = "created_at", updatable = false)
//    private LocalDateTime createdAt;

    private StatutPromotion parseStatus(String s) {
        if (s == null || s.isBlank() || s.equals("Tous")) return null;
        return switch (s) {
            case "Active"     -> StatutPromotion.ACTIVE;
            case "Désactivée"  -> StatutPromotion.DESACTIVEE;
            case "Expirée" -> StatutPromotion.EXPIREE;
            default -> null;
        };
    }

    private String formatStatus(StatutPromotion s) {
        if (s == null) return null;
        return switch (s) {
            case ACTIVE   -> "Active";
            case DESACTIVEE  -> "Désactivée";
            case EXPIREE -> "Expirée";
        };
    }

    private String nullIfBlank(String s) {
        return (s == null || s.isBlank()) ? null : s;
    }
}