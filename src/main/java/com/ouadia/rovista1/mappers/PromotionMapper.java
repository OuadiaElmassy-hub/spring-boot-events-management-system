package com.ouadia.rovista1.mappers;



import com.ouadia.rovista1.dtos.promotion.PromotionRequestDto;
import com.ouadia.rovista1.dtos.promotion.PromotionResponseDto;
import com.ouadia.rovista1.entities.Promotion;
import com.ouadia.rovista1.entities.Promotion;
import com.ouadia.rovista1.repositories.OrganisateurRepository;
import com.ouadia.rovista1.services.implementations.OrganisateurServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class PromotionMapper {
   OrganisateurRepository organisateurRepository;
    public Promotion mappingPromotionDtoRequestToPromotion(PromotionRequestDto dto){
        return Promotion.builder()
                .titre(dto.getTitre())
                .dateDebut(dto.getDateDebut())
                .dateFin(dto.getDateFin())
                .type(dto.getType())
                .estApprove(dto.isEstApprove())
                .organisateur(organisateurRepository.findById(dto.getOrganisateurId()).get())
                .build();
    }
    public static PromotionResponseDto mappingPromotionToPromotionDtoResponse(Promotion e){
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