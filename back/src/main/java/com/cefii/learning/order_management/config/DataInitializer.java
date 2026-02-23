package com.cefii.learning.order_management.config;

import com.cefii.learning.order_management.model.Dish;
import com.cefii.learning.order_management.model.User;
import com.cefii.learning.order_management.repository.DishRepository;
import com.cefii.learning.order_management.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
@Profile("!test")
public class DataInitializer implements CommandLineRunner {

    private final DishRepository dishRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(DishRepository dishRepository,
                           UserRepository userRepository,
                           PasswordEncoder passwordEncoder) {
        this.dishRepository = dishRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        if (userRepository.count() == 0) {
            User client = User.builder()
                    .username("client")
                    .password(passwordEncoder.encode("password"))
                    .role("CLIENT")
                    .build();
            userRepository.save(client);

            User cook = User.builder()
                    .username("cook")
                    .password(passwordEncoder.encode("password"))
                    .role("COOK")
                    .build();
            userRepository.save(cook);
        }

        if (dishRepository.count() == 0) {
            List<Dish> dishes = List.of(
                    Dish.builder().name("Burger classique").description("Burger classique avec steak haché, cheddar, salade et sauce maison").price(new BigDecimal("12.50")).available(true).deleted(false).build(),
                    Dish.builder().name("Pizza Margherita").description("Pizza à la sauce tomate, mozzarella fraîche et basilic").price(new BigDecimal("14.90")).available(true).deleted(false).build(),
                    Dish.builder().name("Pâtes carbonara").description("Pâtes fraîches à la crème, lardons fumés et parmesan").price(new BigDecimal("16.50")).available(true).deleted(false).build(),
                    Dish.builder().name("Saumon grillé").description("Saumon grillé servi avec légumes de saison").price(new BigDecimal("18.00")).available(true).deleted(false).build(),
                    Dish.builder().name("Salade César").description("Salade composée avec poulet grillé, tomates cerises et vinaigrette maison").price(new BigDecimal("9.50")).available(true).deleted(false).build(),
                    Dish.builder().name("Quiche poireaux fromage").description("Quiche maison aux poireaux et fromage").price(new BigDecimal("11.00")).available(true).deleted(false).build(),
                    Dish.builder().name("Soupe maison").description("Soupe du jour préparée avec des légumes frais").price(new BigDecimal("7.50")).available(true).deleted(false).build(),
                    Dish.builder().name("Wrap poulet").description("Wrap garni de poulet mariné, crudités et sauce yaourt").price(new BigDecimal("13.20")).available(true).deleted(false).build(),
                    Dish.builder().name("Entrecôte frites").description("Entrecôte grillée avec frites croustillantes").price(new BigDecimal("15.80")).available(false).deleted(false).build(),
                    Dish.builder().name("Fondant au chocolat").description("Dessert fondant au chocolat avec cœur coulant").price(new BigDecimal("6.90")).available(true).deleted(false).build(),
                    Dish.builder().name("Tarte aux pommes").description("Tarte aux pommes caramélisées").price(new BigDecimal("5.50")).available(true).deleted(false).build(),
                    Dish.builder().name("Risotto aux champignons").description("Risotto crémeux aux champignons").price(new BigDecimal("8.40")).available(true).deleted(false).build()
            );
            dishRepository.saveAll(dishes);
        }
    }
}
