package com.cefii.learning.order_management.dto.response;

import lombok.Data;

@Data
public class OrderItemResponse {
    private Long id;
    private Long orderId;
    private Long dishId;
    private String dishName;
    private Integer quantity;
}
