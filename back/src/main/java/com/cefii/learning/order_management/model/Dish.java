package com.cefii.learning.order_management.model;

import java.math.BigDecimal;
import java.util.List;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Entity
@Table(name = "dish")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Dish {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "dish_id")
    private Long dish_id;

    @NotBlank(message = "Le nom du menu est requis")
    @Size(min = 3, max = 50, message = "Le nom du menu doit contenir entre 3 et 50 caractères")
    @Column(nullable = false)
    private String name;

    @NotBlank(message = "La description du menu est requise")
    @Column(nullable = false)
    private String description;

    @NotNull(message = "Le prix du menu est requis")
    @Positive(message = "Le prix du menu doit être positif")
    @Column(nullable = false, unique = false,  precision = 10, scale = 2)
    private BigDecimal price;

    @Column(nullable = false)
    private boolean available;

    @Column(nullable = false)
    private boolean deleted = false;


}   
