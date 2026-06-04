package com.ouadia.rovista1.services.interfaces;


import com.ouadia.rovista1.dtos.promotion.PromotionRequestDto;
import com.ouadia.rovista1.dtos.promotion.PromotionResponseDto;
import com.ouadia.rovista1.exceptions.PromotionNotFoundException;

import java.util.List;
import java.util.Map;

public interface IPromotionService {
    public PromotionResponseDto addPromotion(PromotionRequestDto promotionDto);
    public PromotionResponseDto editPromotion(PromotionRequestDto promotionDto ,  Long id);
    public PromotionResponseDto editPromotionMap(  Long id , Map<String,Object> map);
    public PromotionResponseDto getPromotionById(  Long id )throws PromotionNotFoundException;
    public List<PromotionResponseDto> getAllPromotions();
    public void deletePromotionById( Long id);
    public void deleteAllByIds(Long ... ids);
}
