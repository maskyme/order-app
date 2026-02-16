package com.cefii.learning.order_management.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.cefii.learning.order_management.model.Dish;

public interface DishRepository 
    extends JpaRepository<Dish, Long> {}