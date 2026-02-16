package com.cefii.learning.order_management.service;

import com.cefii.learning.order_management.repository.DishRepository;

import java.util.List;

import org.springframework.stereotype.Service;
import com.cefii.learning.order_management.model.Dish;

@Service
public class DishService {
    private final DishRepository dishRepository;

    
    public DishService(DishRepository dishRepository) {
        this.dishRepository = dishRepository;
    }

    public Dish getDishById(Long id) {
        return dishRepository.findById(id).orElse(null);
    }

    public Dish createDish(Dish dish) {
        return dishRepository.save(dish);
    }

    public List<Dish> getAlldishes() {
        return dishRepository.findAll();
    }

    public Dish updateDish(Long id, Dish updatedDish) {
        return dishRepository.findById(id).map(dish -> {
            dish.setName(updatedDish.getName());
            dish.setDescription(updatedDish.getDescription());
            dish.setPrice(updatedDish.getPrice());
            dish.setAvailable(updatedDish.isAvailable());
            dish.setDeleted(updatedDish.isDeleted());
            return dishRepository.save(dish);
        }).orElse(null);
    }

    public Dish softDeleteDish(Long id) {
        return dishRepository.findById(id).map(dish -> {
            dish.setDeleted(true);
            dish.setAvailable(false); 
            return dishRepository.save(dish);
        }).orElse(null);
    }
}