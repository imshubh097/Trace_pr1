package com.intro.order_service.order;

import org.springframework.data.jpa.repository.JpaRepository;

public interface   OrderRepository
        extends JpaRepository<Order, Long> {

    Order findByAccountNumber(String accountNumber);
}

