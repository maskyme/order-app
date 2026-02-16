package com.cefii.learning.order_management.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.cefii.learning.order_management.model.OrderItem;

public interface OrderItemRepository 
    extends JpaRepository<OrderItem, Long> {}