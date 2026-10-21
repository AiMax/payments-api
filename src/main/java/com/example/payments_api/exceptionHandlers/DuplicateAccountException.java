package com.example.payments_api.exceptionHandlers;

public class DuplicateAccountException extends RuntimeException {
    public DuplicateAccountException(String id) {
        super("Account %s duplicated.".formatted(id));
    }
}
