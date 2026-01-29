package com.bank.tracking_app.transaction;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    private final RestTemplate restTemplate;
    private final TransactionRepository repository;
    private final KafkaTemplate<String, String> kafkaTemplate;

    public TransactionController(RestTemplate restTemplate,
                                 TransactionRepository repository,
                                 KafkaTemplate<String, String> kafkaTemplate) {
        this.restTemplate = restTemplate;
        this.repository = repository;
        this.kafkaTemplate = kafkaTemplate;
    }

    @PostMapping
    public String doTransaction(@RequestBody Transaction txn) {

        Double balance = restTemplate.getForObject(
                "http://localhost:8080/accounts/"
                        + txn.getAccountNumber() + "/balance",
                Double.class
        );

        txn.setStatus("SUCCESS");
        repository.save(txn);

        kafkaTemplate.send("txn-topic", txn.getAccountNumber());

        return "Transaction successful, balance=" + balance;
    }
}
