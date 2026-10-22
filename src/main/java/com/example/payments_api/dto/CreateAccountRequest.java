package com.example.payments_api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record CreateAccountRequest(@NotBlank(message = "must not be blank") String ownerName, @Pattern(regexp = "[A-Z]{3}", message = "must be a 3-letter ISO code") String currency) {}
