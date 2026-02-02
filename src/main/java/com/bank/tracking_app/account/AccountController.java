package com.bank.tracking_app.account;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/accounts")
public class AccountController {

    private final AccountRepository repository;

    public AccountController(AccountRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/{accountNo}/balance")
    public Double getBalance(@PathVariable String accountNo) {
        return repository.findByAccountNumber(accountNo)
                .orElseThrow()
                .getBalance();
    }
}
