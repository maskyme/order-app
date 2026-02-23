package com.cefii.learning.order_management.dish;

import java.util.List;
import java.math.BigDecimal;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import com.cefii.learning.order_management.model.Dish;
import com.cefii.learning.order_management.repository.DishRepository;
import com.cefii.learning.order_management.service.DishService;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class DishServiceTest {

    @Mock
    private DishRepository dishRepository;

    @InjectMocks
    private DishService dishService;

    @Test
    void shouldReturnAllAvailableDishes() {
        List<Dish> dishes = List.of(
            Dish.builder()
                .name("Burger")
                .description("Bon burger")
                .price(new BigDecimal("10.00"))
                .available(true)
                .deleted(false)
                .build()
        );
        when(dishRepository.findAll()).thenReturn(dishes);

        List<Dish> result = dishService.getAlldishes();

        assertEquals(1, result.size());
        verify(dishRepository).findAll();
    }
}