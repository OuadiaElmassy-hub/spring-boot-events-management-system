package com.pfe.backend.services.interfaces;


import com.pfe.backend.dtos.promotion.PromotionRequestDto;
import com.pfe.backend.dtos.promotion.PromotionResponseDto;
import com.pfe.backend.exceptions.OrganisateurNotFoundException;
import com.pfe.backend.exceptions.PromotionNotFoundException;

import java.util.List;
import java.util.Map;

public interface IPromotionService {
    public PromotionResponseDto addPromotion(PromotionRequestDto promotionDto) throws OrganisateurNotFoundException;
    public PromotionResponseDto editPromotion(PromotionRequestDto promotionDto ,  Long id) throws OrganisateurNotFoundException;
    public PromotionResponseDto editPromotionMap(  Long id , Map<String,Object> map);
    public PromotionResponseDto getPromotionById(  Long id )throws PromotionNotFoundException;
    public List<PromotionResponseDto> getAllPromotions();
    public void deletePromotionById( Long id);
    public void deleteAllByIds(Long ... ids);
}
