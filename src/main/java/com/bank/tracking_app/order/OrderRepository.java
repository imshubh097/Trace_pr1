package com.bank.tracking_app.order;

import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository
        extends JpaRepository<Order, Long> {

    Order findByAccountNumber(String accountNumber);
}

