package com.debashis.ecommerce.order.external.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "CUSTOMER-SERVICE", url = "${application.config.customer-service-url}", path = "/api/v1/customers")
public interface CustomerClient {

    // Add methods to call customer service
    @GetMapping("/exists/{customer-id}")
    public boolean existsCustomer(@PathVariable("customer-id") String customerId);

}
