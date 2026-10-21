package com.example.payments_api.dto;

import jakarta.validation.constraints.NotBlank;

public record CreateAccountRequest(@NotBlank(message = "must not be blank") String ownerName, @NotBlank String currency) {}
