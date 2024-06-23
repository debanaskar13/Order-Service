package com.debashis.ecommerce.order.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.debashis.ecommerce.order.dto.OrderRequest;
import com.debashis.ecommerce.order.dto.OrderResponse;
import com.debashis.ecommerce.order.service.OrderService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService service;

    @PostMapping
    public ResponseEntity<OrderResponse> createOrder(@RequestBody @Validated OrderRequest request) {

        return ResponseEntity.status(HttpStatus.CREATED).body(this.service.createOrder(request));
    }

    @GetMapping("/{order-id}")
    public ResponseEntity<OrderResponse> getOrderById(@PathVariable("order-id") Long orderId) {

        return ResponseEntity.status(HttpStatus.OK).body(this.service.getOrderById(orderId));
    }

    @GetMapping
    public ResponseEntity<List<OrderResponse>> getAllOrder() {

        return ResponseEntity.status(HttpStatus.OK).body(this.service.getAllOrders());
    }

    @PostMapping("/cancel/{order-id}")
    public ResponseEntity<String> updateOrder(@PathVariable("order-id") Long orderId) {

        return ResponseEntity.status(HttpStatus.OK).body(this.service.deleteOrderById(orderId));
    }
}
