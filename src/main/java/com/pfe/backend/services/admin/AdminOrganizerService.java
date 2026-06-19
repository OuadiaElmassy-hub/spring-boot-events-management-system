package com.pfe.backend.services.admin;

import com.pfe.backend.dtos.admin.AdminOrganizerDTO;
import com.pfe.backend.entities.Organisateur;
import com.pfe.backend.entities.enums.StatutCompte;
import com.pfe.backend.entities.enums.StatutOrganisateur;
import com.pfe.backend.entities.enums.StatutPaiement;
import com.pfe.backend.exceptions.OrganisateurNotFoundException;
import com.pfe.backend.repositories.EventRepository;
import com.pfe.backend.repositories.OrganisateurRepository;
import com.pfe.backend.repositories.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AdminOrganizerService {

    private final OrganisateurRepository organisateurRepository;
    private final EventRepository eventRepo;
    private final ReservationRepository reservationRepo;

    public Page<AdminOrganizerDTO> searchOrganizers(
            String search, Boolean verified, int page, int size) {

        Pageable pageable = PageRequest.of(page, size);

        return organisateurRepository.search(
            nullIfBlank(search), verified, pageable
        ).map(this::toDTO);
    }

    @Transactional
    public void verify(Long userId) throws OrganisateurNotFoundException {
        Organisateur op = organisateurRepository.findById(userId)
            .orElseThrow(() -> new OrganisateurNotFoundException("organisateur introuvable"));
        op.setVerified(true);
        op.setEnabled(true);
        op.setStatutOrganisateur(StatutOrganisateur.ACTIF);
        op.setStatutCompte(StatutCompte.ACTIF);
        op.setDateValidation(LocalDateTime.now());
        organisateurRepository.save(op);
    }

    private AdminOrganizerDTO toDTO(Organisateur op) {


        // Calcul revenus et nb événements pour cet organisateur
        long   totalEvents  = eventRepo.countByOrganisateurId(op.getId());
        Double totalRevenue = eventRepo.totalRevenueByOrganisateurId(op.getId(), StatutPaiement.VALIDE);

        return new AdminOrganizerDTO(
            op.getId(),
            op.getNom(),
            op.getEmail(),
            op.getAdresse(),
            op.isVerified(),
            totalEvents,
            totalRevenue
        );
    }

    private String nullIfBlank(String s) {
        return (s == null || s.isBlank()) ? null : s;
    }
}

