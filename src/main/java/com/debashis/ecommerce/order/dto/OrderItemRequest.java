package com.debashis.ecommerce.order.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record OrderItemRequest(
        Long id,
        @NotNull(message = "Product ID is required") Long productId,
        @Positive(message = "Quantity should be positive") double quantity) {

}
