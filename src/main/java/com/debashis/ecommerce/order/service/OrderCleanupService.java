package com.debashis.ecommerce.order.service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.debashis.ecommerce.order.model.OrderStatus;
import com.debashis.ecommerce.order.repository.OrderRepository;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class OrderCleanupService {

    @Autowired
    private OrderRepository repo;

    @Value("${application.config.cron.order-older-than}")
    private Long olderThan;

    @Scheduled(cron = "${application.config.cron.expression}")
    @Transactional
    public void deleteOlderOrder() {

        log.info("Running order cleanup job...");

        try {

            LocalDateTime cutOffDate = LocalDateTime.now().minus(olderThan, ChronoUnit.MINUTES);
            int deletedOrderCount = this.repo.deleteByStatusAndCreatedAtBefore(OrderStatus.CANCELLED, cutOffDate);

            log.info("Deleted {} orders older than {}", deletedOrderCount, cutOffDate);
            log.info("Order cleanup job completed successfully.");
        } catch (Exception e) {
            log.error(e.getLocalizedMessage(), e);
        }
    }

}
