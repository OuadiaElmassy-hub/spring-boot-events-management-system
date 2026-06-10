package com.ouadia.rovista1.services.implementations;


import com.ouadia.rovista1.dtos.promotion.PromotionRequestDto;
import com.ouadia.rovista1.dtos.promotion.PromotionResponseDto;
import com.ouadia.rovista1.entities.*;
import com.ouadia.rovista1.entities.Promotion;

import com.ouadia.rovista1.entities.enums.TypePromotion;
import com.ouadia.rovista1.exceptions.OrganisateurNotFoundException;
import com.ouadia.rovista1.exceptions.PromotionNotFoundException;
import com.ouadia.rovista1.mappers.PromotionMapper;
import com.ouadia.rovista1.repositories.PromotionRepository;
import com.ouadia.rovista1.services.interfaces.IPromotionService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
@Transactional
@AllArgsConstructor
public class PromotionServiceImpl implements IPromotionService {

    private PromotionRepository repository;
    private PromotionMapper promotionMapper;


    @Override
    public PromotionResponseDto addPromotion(PromotionRequestDto promotionDto) throws OrganisateurNotFoundException {
        Promotion promotion= promotionMapper.mappingPromotionDtoRequestToPromotion(promotionDto);
            return promotionMapper.mappingPromotionToPromotionDtoResponse(repository.save(promotion));
    }

    @Override
    public PromotionResponseDto editPromotion(PromotionRequestDto promotionDto, Long id) throws OrganisateurNotFoundException {
        Promotion promotion= promotionMapper.mappingPromotionDtoRequestToPromotion(promotionDto);
        if (promotion==null)return null;
        else {
            Promotion promotion1 =repository.findById(id).get();
            if (promotion1==null)return null;
            promotion1.setTitre(promotion.getTitre());
            promotion1.setDateDebut(promotion.getDateDebut());
            promotion1.setDateFin(promotion.getDateFin());
            promotion1.setType(promotion.getType());
            promotion1.setEstApprove(promotion.isEstApprove());
            promotion1.setOrganisateur(promotion.getOrganisateur());
            promotion1.setClients(promotion.getClients());
            promotion1.setEvenements(promotion.getEvenements());
            return promotionMapper.mappingPromotionToPromotionDtoResponse(repository.save(promotion1));
        }
    }

    @Override
    public PromotionResponseDto editPromotionMap(Long id, Map<String, Object> map) {
        if (map == null ) return null;
        else {
            Promotion promotion1 = repository.findById(id).get();
            if (promotion1 == null) {
                return null;
            }
            if (map.containsKey("titre")) {
                promotion1.setTitre((String) map.get("titre"));
            }
            if (map.containsKey("dateDebut")) {
                promotion1.setDateDebut((LocalDateTime) map.get("dateDebut"));
            }
            if (map.containsKey("dateFin")) {
                promotion1.setDateFin((LocalDateTime) map.get("dateFin"));
            }
            if (map.containsKey("typePromotion")) {
                promotion1.setType(TypePromotion.valueOf(map.get("typePromotion").toString()));
            }
            if (map.containsKey("estApprove")) {
                promotion1.setEstApprove((Boolean) map.get("estApprove"));
            }
            if (map.containsKey("organisateur")) {
                promotion1.setOrganisateur((Organisateur) map.get("organisateur"));
            }
            if (map.containsKey("clients")) {
                promotion1.setClients((List<Client>) map.get("clients"));
            }
            if (map.containsKey("evenements")) {
                promotion1.setEvenements((List<Evenement>) map.get("evenements"));
            }
            return promotionMapper.mappingPromotionToPromotionDtoResponse(repository.save(promotion1));
        }
    }

    @Override
    public PromotionResponseDto getPromotionById(Long id) throws PromotionNotFoundException {
        Promotion promotion = repository.findById(id).orElseThrow(() -> new PromotionNotFoundException("Promotion not found"));
        return promotionMapper.mappingPromotionToPromotionDtoResponse(promotion);
    }

    @Override
    public List<PromotionResponseDto> getAllPromotions() {
        return (repository.findAll().stream().map(promotion-> promotionMapper.mappingPromotionToPromotionDtoResponse(promotion)).toList());

    }

    @Override
    public void deletePromotionById(Long id) {
        repository.deleteById(id);
    }

    @Override
    public void deleteAllByIds(Long... ids) {
        for (Long id :ids){
            deletePromotionById(id);
        }
    }
}
