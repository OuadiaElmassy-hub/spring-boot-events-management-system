package com.pfe.backend.mappers;



import com.pfe.backend.dtos.promotion.PromotionRequestDto;
import com.pfe.backend.dtos.promotion.PromotionResponseDto;
import com.pfe.backend.entities.Promotion;
import com.pfe.backend.exceptions.OrganisateurNotFoundException;
import com.pfe.backend.repositories.OrganisateurRepository;
import com.pfe.backend.services.implementations.OrganisateurServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class PromotionMapper {
   OrganisateurRepository organisateurRepository;
   OrganisateurServiceImpl organisateurService;
    public Promotion mappingPromotionDtoRequestToPromotion(PromotionRequestDto dto) throws OrganisateurNotFoundException {
        return Promotion.builder()
                .titre(dto.getTitre())
                .dateDebut(dto.getDateDebut())
                .dateFin(dto.getDateFin())
                .type(dto.getType())
                .estApprove(dto.isEstApprove())
                .organisateur(organisateurService.getOrganisateurEntityById(dto.getOrganisateurId()))
                .build();
    }
    public PromotionResponseDto mappingPromotionToPromotionDtoResponse(Promotion e){
        return PromotionResponseDto.builder()
                .titre(e.getTitre())
                .dateDebut(e.getDateDebut())
                .dateFin(e.getDateFin())
                .type(e.getType())
                .estApprove(e.isEstApprove())
                .organisateurId(e.getId())
                .build();
    }
}