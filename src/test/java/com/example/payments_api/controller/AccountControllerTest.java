package com.example.payments_api.controller;

import com.example.payments_api.dto.AccountResponse;
import com.example.payments_api.exceptionHandlers.AccountNotFoundException;
import com.example.payments_api.exceptionHandlers.DuplicateAccountException;
import com.example.payments_api.service.AccountService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(AccountController.class)
public class AccountControllerTest {

    @Autowired
    MockMvc mockMvc; // Simulates HTTP request without web.

    @MockitoBean
    AccountService service;

    @Test
    void createReturn201AndLocation() throws Exception {
        when(service.create(any()))
                .thenReturn(new AccountResponse("abc", "Ana", "EUR"));

        mockMvc.perform(post("/api/v1/accounts")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"ownerName\":\"Ana\",\"currency\":\"EUR\"}"))

                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "/api/v1/accounts/abc"))
                .andExpect(jsonPath("$.id").value("abc"));
    }

    @Test
    void createInvalidReturn400() throws Exception {

        mockMvc.perform(post("/api/v1/accounts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"ownerName\":\"\",\"currency\":\"EURO\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith("application/problem+json"))
                .andExpect(jsonPath("$.errors.ownerName").exists())
                .andExpect(jsonPath("$.errors.currency").exists());

        verifyNoInteractions(service);
    }

    @Test
    void getMissingAccountReturn404() throws Exception {
        when(service.find("s")).thenThrow(new AccountNotFoundException("s"));

        mockMvc.perform(get("/api/v1/accounts/{id}", "s"))

                .andExpect(status().isNotFound())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_PROBLEM_JSON))
                .andExpect(jsonPath("$.title").value("Account not found"))
                .andExpect(jsonPath("$.detail").value("Account s not found."))
        ;
    }

    @Test
    void createDuplicateAccountReturn409() throws Exception {

        when(service.create(any())).thenThrow(new DuplicateAccountException("abc"));

        mockMvc.perform(post("/api/v1/accounts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"ownerName\":\"Ana\",\"currency\":\"EUR\"}"))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.title").value("Duplicate Account"))
                .andExpect(jsonPath("$.detail").value("Account abc duplicated."));

    }



}
