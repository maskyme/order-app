package com.cefii.learning.order_management.controller;

import com.cefii.learning.order_management.model.Order;
import com.cefii.learning.order_management.model.User;
import com.cefii.learning.order_management.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody User user) {
        return ResponseEntity.ok(userService.registerUser(user));
    }

    @PreAuthorize("hasRole('COOK')")
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }
    
    @PreAuthorize("hasAnyRole('CLIENT','COOK')")
    @GetMapping("/{id}/orders")
    public ResponseEntity<List<Order>> getOrdersByUser(@PathVariable Long id) {
        List<Order> orders = userService.getOrdersByUser(id);
        return orders != null
                ? ResponseEntity.ok(orders)
                : ResponseEntity.notFound().build();
    }
}
