package com.debashis.ecommerce.order.service;

import java.util.List;

import com.debashis.ecommerce.order.dto.OrderRequest;
import com.debashis.ecommerce.order.dto.OrderResponse;

public interface OrderService {
    OrderResponse createOrder(OrderRequest request);

    List<OrderResponse> getAllOrders();

    OrderResponse getOrderById(Long orderId);

    String deleteOrderById(Long orderId);
}
