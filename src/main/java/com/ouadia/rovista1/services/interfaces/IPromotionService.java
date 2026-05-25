package com.ouadia.rovista1.services.interfaces;


import com.ouadia.rovista1.dtos.PromotionDto;
import com.ouadia.rovista1.entities.Promotion;
import com.ouadia.rovista1.exceptions.PromotionNotFoundException;

import java.util.List;
import java.util.Map;

public interface IPromotionService {
    public PromotionDto addPromotion(PromotionDto promotionDto);
    public PromotionDto editPromotion(PromotionDto promotionDto ,  Long id);
    public PromotionDto editPromotionMap(  Long id , Map<String,Object> map);
    public PromotionDto getPromotionById(  Long id )throws PromotionNotFoundException;
    public List<PromotionDto> getAllPromotions();
    public void deletePromotionById( Long id);
    public void deleteAllByIds(Long ... ids);
}
