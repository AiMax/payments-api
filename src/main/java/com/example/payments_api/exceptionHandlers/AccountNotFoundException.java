package com.example.payments_api.exceptionHandlers;

public class AccountNotFoundException extends RuntimeException {
    public AccountNotFoundException(String id) {
        super("Account %s not found.".formatted(id));
    }
}
