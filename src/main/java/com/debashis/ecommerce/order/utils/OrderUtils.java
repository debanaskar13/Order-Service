package com.debashis.ecommerce.order.utils;

import org.springframework.stereotype.Service;

import com.debashis.ecommerce.order.dto.OrderItemResponse;
import com.debashis.ecommerce.order.dto.OrderResponse;
import com.debashis.ecommerce.order.model.Order;
import com.debashis.ecommerce.order.model.OrderItem;

@Service
public class OrderUtils {

    public OrderResponse toOrderResponse(Order order) {
        return new OrderResponse(
                order.getId(),
                order.getCustomerId(),
                order.getItems()
                        .stream()
                        .map(this::toOrderItemResponse)
                        .toList(),
                order.getStatus(),
                order.getTotalAmount(),
                order.getCreatedAt());
    }

    public OrderItemResponse toOrderItemResponse(OrderItem orderItem) {
        return new OrderItemResponse(
                orderItem.getId(),
                orderItem.getProductId(),
                orderItem.getProductName(),
                orderItem.getPrice(),
                orderItem.getQuantity());
    }

    public OrderItem toOrderItem(OrderItemResponse request) {
        return OrderItem.builder()
                .productId(request.productId())
                .productName(request.name())
                .price(request.price())
                .quantity(request.quantity())
                .build();
    }

}
