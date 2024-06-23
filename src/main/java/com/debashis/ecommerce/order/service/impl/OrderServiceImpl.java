package com.debashis.ecommerce.order.service.impl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;

import com.debashis.ecommerce.order.dto.OrderItemRequest;
import com.debashis.ecommerce.order.dto.OrderItemResponse;
import com.debashis.ecommerce.order.dto.OrderRequest;
import com.debashis.ecommerce.order.dto.OrderResponse;
import com.debashis.ecommerce.order.exception.OrderNotFoundException;
import com.debashis.ecommerce.order.exception.ResourceNotFoundException;
import com.debashis.ecommerce.order.external.service.CustomerClient;
import com.debashis.ecommerce.order.external.service.ProductClient;
import com.debashis.ecommerce.order.model.Order;
import com.debashis.ecommerce.order.model.OrderStatus;
import com.debashis.ecommerce.order.repository.OrderRepository;
import com.debashis.ecommerce.order.service.OrderService;
import com.debashis.ecommerce.order.utils.OrderUtils;

import feign.FeignException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository repository;
    private final OrderUtils orderUtils;
    private final CustomerClient customerClient;
    private final ProductClient productClient;

    @Override
    public OrderResponse createOrder(OrderRequest request) {
        // Check if Customer is exists or not

        boolean customerExist = this.customerClient.existsCustomer(request.customerId());
        if (!customerExist) {
            throw new ResourceNotFoundException("Customer with ID :: " + request.customerId() + " not found");
        }

        List<OrderItemRequest> orderItemRequestList = request.items();

        // Send Purchase Product request to product service
        List<OrderItemResponse> purchasedProducts = null;
        try {
            purchasedProducts = this.productClient.purchaseProducts(orderItemRequestList);

        } catch (FeignException e) {
            throw new RuntimeException(e.getMessage());
        }
        if (purchasedProducts.isEmpty()) {
            throw new RuntimeException("Failed to purchase products");
        }

        Order order = new Order();
        order.setCustomerId(request.customerId());
        BigDecimal totalAmount = new BigDecimal(0);
        for (OrderItemResponse orderItemResponse : purchasedProducts) {
            order.addItem(this.orderUtils.toOrderItem(orderItemResponse));
            totalAmount = totalAmount.add(orderItemResponse.price());
        }
        order.setStatus(OrderStatus.CREATED);
        order.setTotalAmount(totalAmount);

        // Payment service

        // Save the Order

        Order savedOrder = this.repository.save(order);

        // Notification Service

        return this.orderUtils.toOrderResponse(savedOrder);
    }

    @Override
    public List<OrderResponse> getAllOrders() {
        return this.repository.findAll().stream().map(this.orderUtils::toOrderResponse).toList();
    }

    @Override
    public OrderResponse getOrderById(Long orderId) {
        return this.repository.findById(orderId).map(this.orderUtils::toOrderResponse)
                .orElseThrow(() -> new OrderNotFoundException("Order not found with ID :: " + orderId));
    }

    @Override
    public String deleteOrderById(Long orderId) {
        Order order = this.repository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException("Order not found with ID :: " + orderId));

        order.setStatus(OrderStatus.CANCELLED);

        this.repository.save(order);

        return "Order canceled successfully";
    }

}
