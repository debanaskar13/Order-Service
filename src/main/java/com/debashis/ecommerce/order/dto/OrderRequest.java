package com.debashis.ecommerce.order.dto;

import java.util.List;

import jakarta.validation.constraints.NotNull;

public record OrderRequest(
                @NotNull(message = "Customer ID is required") String customerId,
                List<OrderItemRequest> items) {

}
