package com.andre.balancesheet.dto;

import com.andre.balancesheet.config.adapter.LocalDateTimeTypeAdapter;
import com.andre.balancesheet.config.adapter.LocalDateTypeAdapter;
import com.andre.balancesheet.model.TypeEnum;
import com.google.gson.annotations.JsonAdapter;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Builder(toBuilder = true)
public record BalanceDto(
        @NotNull
        Double amount,
        @NotNull
        @NotBlank
        String description,
        @NotNull
        TypeEnum type,
        @JsonAdapter(LocalDateTypeAdapter.class)
        LocalDate date,
        @JsonAdapter(LocalDateTimeTypeAdapter.class)
        LocalDateTime createdAt
) {
    public static class BalanceDtoBuilder {
        LocalDate date = LocalDate.now();
        LocalDateTime createdAt = LocalDateTime.now();
    }
}
