package com.cefii.learning.order_management.controller;

import com.cefii.learning.order_management.model.Order;
import com.cefii.learning.order_management.model.enums.OrderStatus;
import com.cefii.learning.order_management.service.OrderService;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PreAuthorize("hasAnyRole('CLIENT','COOK')")
    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable Long id) {
        Order order = orderService.getOrderById(id);
        return order != null
                ? ResponseEntity.ok(order)
                : ResponseEntity.notFound().build();
    }

    @PreAuthorize("hasRole('CLIENT')")
    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestBody Order order) {
        return ResponseEntity.ok(orderService.createOrder(order));
    }

    @PreAuthorize("hasAnyRole('CLIENT','COOK')")
    @GetMapping
    public ResponseEntity<List<Order>> getAllOrders() {
        List<Order> orders = orderService.getAllOrders();
        return ResponseEntity.ok(orders);
    }

    @PreAuthorize("hasRole('COOK')")
    @PatchMapping("/{id}/status")
    public ResponseEntity<Order> updateOrderStatus(
            @PathVariable Long id,
            @RequestBody OrderStatus status
    ) {
        Order updated = orderService.updateOrderStatus(id, status);
        return updated != null
                ? ResponseEntity.ok(updated)
                : ResponseEntity.notFound().build();
    }
}
