package com.ouadia.rovista1.services.interfaces;


import com.ouadia.rovista1.entities.Promotion;

import java.util.List;

public interface IPromotionService {
    public Promotion addPromotion(Promotion promotion);
    public Promotion editPromotion(Promotion promotion);
    public Promotion getPromotionById(Long id);
    public List<Promotion> getAllPromotions();
    public void deletePromotionById(Long id);
}
