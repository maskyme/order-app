package com.cefii.learning.order_management.repository;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.boot.security.autoconfigure.SecurityAutoConfiguration;
import org.springframework.boot.security.autoconfigure.web.servlet.SecurityFilterAutoConfiguration;
import org.springframework.test.context.ActiveProfiles;

import com.cefii.learning.order_management.model.Dish;

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@ImportAutoConfiguration(exclude = {
    SecurityAutoConfiguration.class,
    SecurityFilterAutoConfiguration.class
})
class DishRepositoryTest {

    @Autowired
    private DishRepository dishRepository;

    @Test
    void shouldSaveDish() {

        Dish dish = Dish.builder()
                .name("Pizza")
                .description("Bonne pizza")
                .price(new BigDecimal("12.00"))
                .available(true)
                .deleted(false)
                .build();

        Dish saved = dishRepository.save(dish);

        assertNotNull(saved.getDish_id());
    }
}