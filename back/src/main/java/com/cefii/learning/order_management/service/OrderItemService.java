package com.cefii.learning.order_management.service;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import com.cefii.learning.order_management.dto.request.OrderItemRequest;
import com.cefii.learning.order_management.model.Order;
import com.cefii.learning.order_management.model.Dish;
import com.cefii.learning.order_management.model.OrderItem;
import com.cefii.learning.order_management.repository.OrderRepository;
import com.cefii.learning.order_management.repository.DishRepository;
import com.cefii.learning.order_management.repository.OrderItemRepository;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderItemService {

    @Autowired private OrderRepository orderRepository;
    @Autowired private DishRepository dishRepository;
    @Autowired private OrderItemRepository orderItemRepository;

    @Transactional
    public OrderItem createOrderItem(OrderItemRequest request) {
        Order order = orderRepository.findById(request.getOrderId())
                .orElseThrow(() -> new RuntimeException("Order not found"));

        Dish dish = dishRepository.findById(request.getDishId())
                .orElseThrow(() -> new RuntimeException("Dish not found"));

        OrderItem orderItem = OrderItem.builder()
                .order(order)
                .dish(dish)
                .quantity(request.getQuantity())
                .build();

        return orderItemRepository.save(orderItem);
    }
}
