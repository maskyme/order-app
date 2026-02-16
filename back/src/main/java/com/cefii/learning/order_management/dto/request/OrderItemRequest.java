package com.cefii.learning.order_management.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class OrderItemRequest {
    @NotNull
    private Long orderId;

    @NotNull
    private Long dishId;

    @NotNull
    @Positive
    private Integer quantity;
}
