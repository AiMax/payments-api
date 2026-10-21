package com.example.payments_api.service;

import com.example.payments_api.dto.AccountResponse;
import com.example.payments_api.dto.CreateAccountRequest;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class AccountService {

    private final Map<String, AccountResponse> accounts = new ConcurrentHashMap<>();

    public Optional<AccountResponse> find(String id){
        return Optional.ofNullable(accounts.get(id));
    }

    public AccountResponse create(CreateAccountRequest req){
        var account = new AccountResponse(UUID.randomUUID().toString(), req.ownerName(), req.currency());
        accounts.put(account.id(), account);
        return account;
    }

}
