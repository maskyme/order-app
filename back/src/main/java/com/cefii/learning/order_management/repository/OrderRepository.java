package com.cefii.learning.order_management.repository;

import com.cefii.learning.order_management.model.Order;
import com.cefii.learning.order_management.model.User;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByClient_Id(Long clientId);

    List<Order> findByClient(User client);
}
