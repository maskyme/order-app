package com.cefii.learning.order_management.controller;

import com.cefii.learning.order_management.model.Dish;
import com.cefii.learning.order_management.service.DishService;

import jakarta.validation.Valid;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/dishes")
public class DishController {

    private final DishService dishService;

    public DishController(DishService dishService) {
        this.dishService = dishService;
    }

    @PreAuthorize("hasAnyRole('CLIENT','COOK')")
    @GetMapping("/{id}")
    public ResponseEntity<Dish> getDishById(@PathVariable Long id) {
        Dish dish = dishService.getDishById(id);
        return dish != null
                ? ResponseEntity.ok(dish)
                : ResponseEntity.notFound().build();
    }

    @PreAuthorize("hasRole('COOK')")
    @PostMapping
    public ResponseEntity<Dish> createDish(@RequestBody Dish dish) {
        return ResponseEntity.ok(dishService.createDish(dish));
    }

    @PreAuthorize("hasAnyRole('CLIENT','COOK')")
    @GetMapping
    public ResponseEntity<List<Dish>> getAllDishes() {
        List<Dish> dishes = dishService.getAlldishes();
        return ResponseEntity.ok(dishes);
    }

    @PreAuthorize("hasRole('COOK')")
    @PutMapping("/{id}")
    public ResponseEntity<Dish> updateDish(
            @PathVariable Long id,
            @Valid @RequestBody Dish dish
    ) {
        Dish updated = dishService.updateDish(id, dish);
        return updated != null
                ? ResponseEntity.ok(updated)
                : ResponseEntity.notFound().build();
    }

    @PreAuthorize("hasRole('COOK')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDish(@PathVariable Long id) {
        Dish deleted = dishService.softDeleteDish(id);
        return deleted != null
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}
