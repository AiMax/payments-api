package com.example.payments_api.dto;

import jakarta.validation.constraints.NotBlank;

public record CreateAccountRequest(@NotBlank String ownerName, @NotBlank String currency) {}
