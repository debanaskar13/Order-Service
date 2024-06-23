package com.debashis.ecommerce.order.dto;

import java.math.BigDecimal;

public record OrderItemResponse(
                Long id,
                Long productId,
                String name,
                BigDecimal price,
                double quantity) {

}
