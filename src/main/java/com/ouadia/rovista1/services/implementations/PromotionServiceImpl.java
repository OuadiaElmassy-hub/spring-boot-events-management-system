package com.ouadia.rovista1.services.implementations;

import com.ouadia.rovista1.entities.Promotion;
import com.ouadia.rovista1.repositories.PromotionRepository;
import com.ouadia.rovista1.services.interfaces.IPromotionService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class PromotionServiceImpl implements IPromotionService {

    private PromotionRepository repository;


    @Override
    public Promotion addPromotion(Promotion promotion) {
        return repository.save(promotion);
    }

    @Override
    public Promotion editPromotion(Promotion promotion) {
        return repository.save(promotion);
    }

    @Override
    public Promotion getPromotionById(Long id) {
        return repository.findById(id).orElseThrow();
    }

    @Override
    public List<Promotion> getAllPromotions() {
        return repository.findAll();
    }

    @Override
    public void deletePromotionById(Long id) {
        repository.deleteById(id);
    }
}
