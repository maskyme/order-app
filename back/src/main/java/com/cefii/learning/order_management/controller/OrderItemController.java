package com.cefii.learning.order_management.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import com.cefii.learning.order_management.dto.request.OrderItemRequest;
import com.cefii.learning.order_management.dto.response.OrderItemResponse;
import com.cefii.learning.order_management.model.OrderItem;
import com.cefii.learning.order_management.service.OrderItemService;

@RestController
@RequestMapping("/api/order-items")
public class OrderItemController {

    @Autowired
    private OrderItemService orderItemService;

    @PostMapping
    public ResponseEntity<OrderItemResponse> createOrderItem(@RequestBody @Valid OrderItemRequest request) {

        OrderItem orderItem = orderItemService.createOrderItem(request);

        // Map entity -> response DTO
        OrderItemResponse response = new OrderItemResponse();
        response.setId(orderItem.getId());
        response.setOrderId(orderItem.getOrder().getId());
        response.setDishId(orderItem.getDish().getDish_id());
        response.setDishName(orderItem.getDish().getName());
        response.setQuantity(orderItem.getQuantity());

        return ResponseEntity.ok(response);
    }
}
