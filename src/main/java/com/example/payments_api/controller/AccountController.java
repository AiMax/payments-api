package com.example.payments_api.controller;

import com.example.payments_api.dto.AccountResponse;
import com.example.payments_api.dto.CreateAccountRequest;
import com.example.payments_api.exceptionHandlers.AccountNotFoundException;
import com.example.payments_api.service.AccountService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.net.URI;

@RestController
@RequestMapping("/api/v1/accounts")
public class AccountController {

    private final AccountService service;


    AccountController(AccountService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    AccountResponse get(@PathVariable String id){
        return service.find(id)
                .orElseThrow(()-> new AccountNotFoundException(id));
    }

    @PostMapping
    ResponseEntity<AccountResponse> create(@Valid @RequestBody CreateAccountRequest req) {
        var created = service.create(req);
        return ResponseEntity.created(URI.create("/api/v1/accounts/" + created.id())).body(created);
    }

}
