package com.debashis.ecommerce.order.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import com.debashis.ecommerce.order.model.OrderStatus;

public record OrderResponse(
        Long id,
        String customerId,
        List<OrderItemResponse> items,
        OrderStatus status,
        BigDecimal totalAmount,
        LocalDateTime orderDate) {

}
