package com.cefii.learning.order_management.service;

import com.cefii.learning.order_management.repository.OrderRepository;

import jakarta.transaction.Transactional;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.cefii.learning.order_management.model.CustomUserDetails;
import com.cefii.learning.order_management.model.Order;
import com.cefii.learning.order_management.model.enums.OrderStatus;
import com.cefii.learning.order_management.model.User;


@Service
public class OrderService {
    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public Order getOrderById(Long id) {
        return orderRepository.findById(id).orElse(null);
    }

    @Transactional
    public Order createOrder(Order order) {

        Authentication auth = SecurityContextHolder
                .getContext()
                .getAuthentication();

        CustomUserDetails userDetails =
                (CustomUserDetails) auth.getPrincipal();

        User client = (User) userDetails.getUser();

        order.setClient(client);
        order.setStatus(OrderStatus.CREATED);

        return orderRepository.save(order);
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Order updateOrderStatus(Long orderId, OrderStatus status) {
        return orderRepository.findById(orderId).map(order -> {
            order.setStatus(status);
            return orderRepository.save(order);
        }).orElse(null);
    }

}