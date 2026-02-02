package com.intro.order_service.order;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderRepository repository;

    public OrderController(OrderRepository repository) {
        this.repository = repository;
    }

    @PostMapping
    public String placeOrder(@RequestBody Order order) {
        order.setStatus("PENDING");
        repository.save(order);
        return "Order placed";
    }

    @KafkaListener(topics = "txn-topic", groupId = "order-group")
    public void consume(String accountNo) {
        Order order = repository.findByAccountNumber(accountNo);
        if(order != null) {
            order.setStatus("COMPLETED");
            repository.save(order);
        }
    }
}

