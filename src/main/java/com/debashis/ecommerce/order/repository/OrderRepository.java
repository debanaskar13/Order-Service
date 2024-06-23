package com.debashis.ecommerce.order.repository;

import java.time.LocalDateTime;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.debashis.ecommerce.order.model.Order;
import com.debashis.ecommerce.order.model.OrderStatus;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    int deleteByStatusAndCreatedAtBefore(OrderStatus status, LocalDateTime dateBefore);

}
