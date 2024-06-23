package com.debashis.ecommerce.order.external.service;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.debashis.ecommerce.order.dto.OrderItemRequest;
import com.debashis.ecommerce.order.dto.OrderItemResponse;

@FeignClient(name = "PRODUCT-SERVICE", url = "${application.config.product-service-url}", path = "/api/v1/products")
public interface ProductClient {

    @PostMapping("/purchase")
    List<OrderItemResponse> purchaseProducts(@RequestBody List<OrderItemRequest> request);
}
