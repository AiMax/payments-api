package com.example.payments_api.controller;

import com.example.payments_api.dto.AccountResponse;
import com.example.payments_api.dto.CreateAccountRequest;
import com.example.payments_api.service.AccountService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

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
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found"));
    }

    @PostMapping
    ResponseEntity<AccountResponse> create(@Valid @RequestBody CreateAccountRequest req) {
        var created = service.create(req);
        return ResponseEntity.created(URI.create("/api/v1/accounts/" + created.id())).body(created);
    }

}
